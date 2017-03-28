package com.wakeup.forever.wakeup.http.download;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Wesly186 on 2016/5/31.
 */
public class DownloadUtils {

    private static final int UPDATE_PROGRESS = 0x02;
    private static final int FAILURE = 0x03;
    private static DownloadUtils downloadUtils = null;
    private static Map<String, RequestCallBack> requestCallBacks;

    //主线程Handler
    private static Handler mHandler = null;

    private DownloadUtils() {
        mHandler = new UIHandler(Looper.getMainLooper(), this);
        requestCallBacks = new HashMap<String, RequestCallBack>();
    }

    public static DownloadUtils getInstance() {
        if (downloadUtils == null) {
            synchronized (DownloadUtils.class) {
                if (downloadUtils == null) {
                    downloadUtils = new DownloadUtils();
                }
            }
        }
        return downloadUtils;
    }

    public Call download(String url, final String path, final String fileName, RequestCallBack callBack) {
        final String key = url + Math.random() * 1000;
        requestCallBacks.put(key, callBack);
        //构造请求
        Request request = null;
        try {
            request = new Request.Builder()
                    .url(url)
                    .build();
        } catch (IllegalArgumentException e) {
            requestCallBacks.get(key).onFailure(null, new Exception("请求参数异常"));
            requestCallBacks.remove(key);
            return null;
        }


        //设置超时，拦截器
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(1000, TimeUnit.MINUTES)
                .writeTimeout(1000, TimeUnit.MINUTES)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Response orginalResponse = chain.proceed(chain.request());
                        return orginalResponse.newBuilder()
                                .body(new ProgressResponseBody(orginalResponse.body(), new ProgressListener() {
                                    @Override
                                    public void onProgress(long progress, long total, boolean done) {
                                        //通过Handler发送进度消息
                                        Message message = Message.obtain();
                                        message.obj = new ProgressModel(key, progress, total, done);
                                        message.what = UPDATE_PROGRESS;
                                        mHandler.sendMessage(message);
                                    }
                                }))
                                .build();
                    }
                })
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //通过Handler发送失败消息
                Message msg = Message.obtain();
                msg.obj = new FailureModel(key, call, e);
                msg.what = FAILURE;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                InputStream in = null;
                OutputStream out = null;
                byte[] buffer = new byte[1024];
                int len = 0;
                try {
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    in = response.body().byteStream();
                    out = new FileOutputStream(new File(dir, fileName));

                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return call;
    }

    //回调接口
    public interface RequestCallBack {
        void onProgress(long progress, long total, boolean done);

        void onFailure(Call call, Exception e);
    }

    //处理UI层的Handler子类
    private static class UIHandler extends Handler {
        //弱引用
        private final WeakReference<DownloadUtils> weakReference;

        public UIHandler(Looper looper, DownloadUtils downloadUtils) {
            super(looper);
            weakReference = new WeakReference<DownloadUtils>(downloadUtils);
        }

        @Override
        public void handleMessage(Message msg) {
            DownloadUtils downloadUtils = null;
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    downloadUtils = weakReference.get();
                    if (downloadUtils != null) {
                        //获得进度实体类
                        ProgressModel progressModel = (ProgressModel) msg.obj;

                        if (requestCallBacks.get(progressModel.getKey()) != null) {
                            requestCallBacks.get(progressModel.getKey()).onProgress(progressModel.getCurrentBytes(), progressModel.getContentLength(), progressModel.isDone());
                            //移除订阅
                            if (progressModel.isDone()) {
                                requestCallBacks.remove(progressModel.getKey());
                            }
                        }
                    }
                    break;
                case FAILURE:
                    downloadUtils = weakReference.get();
                    if (downloadUtils != null) {
                        //获得失败实体类
                        FailureModel failureModel = (FailureModel) msg.obj;
                        if (requestCallBacks.get(failureModel.getKey()) != null) {
                            requestCallBacks.get(failureModel.getKey()).onFailure(failureModel.getCall(), failureModel.getE());
                            requestCallBacks.remove(failureModel.getKey());
                        }
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
}
