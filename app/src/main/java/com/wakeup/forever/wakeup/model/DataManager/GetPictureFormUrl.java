package com.wakeup.forever.wakeup.model.DataManager;

import com.squareup.okhttp.OkHttpClient;
import com.wakeup.forever.wakeup.config.Api;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.SplashImage;
import com.wakeup.forever.wakeup.model.service.ImageService;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by forever on 2016/8/2.
 */
public class GetPictureFormUrl {

    private Retrofit retrofit;

    private static final int DEFAULT_TIMEOUT = 5;

    private ImageService imageService;

    private GetPictureFormUrl() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        imageService=retrofit.create(ImageService.class);
    }

    public void getImage(Subscriber<HttpResult<SplashImage>> subscriber) {
        imageService.getSplash()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static GetPictureFormUrl getInstance(){
        return new GetPictureFormUrl();
    }
}
