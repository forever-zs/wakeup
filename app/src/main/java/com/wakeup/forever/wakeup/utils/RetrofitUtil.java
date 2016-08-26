package com.wakeup.forever.wakeup.utils;

import com.squareup.okhttp.OkHttpClient;
import com.wakeup.forever.wakeup.config.Api;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by forever on 2016/8/19.
 */
public class RetrofitUtil {
    private static Retrofit retrofit;

    private static final int DEFAULT_TIMEOUT = 5;

    static{
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static Retrofit getRetrofit(){
        if(retrofit==null){
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASEURL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
