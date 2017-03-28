package com.wakeup.forever.wakeup.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.jude.beam.Beam;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.wakeup.forever.wakeup.utils.LogUtil;

import java.io.File;

/**
 * Created by forever on 2016/8/1.
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Beam.init(this);
        //友盟推送全局初始化
        PushAgent mPushAgent = PushAgent.getInstance(this);
            //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtil.e("ym:"+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtil.e("ym:"+s+":"+s1);
            }
        });

        //分享
        PlatformConfig.setQQZone("1105616507", "1KCkb5HnmYryOS2G");
        UMShareAPI.get(this);
    }



    public static Context getGlobalContext() {
        return context;
    }
}
