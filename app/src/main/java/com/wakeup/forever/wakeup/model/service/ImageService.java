package com.wakeup.forever.wakeup.model.service;

import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.SplashImage;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by forever on 2016/8/4.
 */
public interface ImageService {
    @GET("image/splash.do")
    Observable<HttpResult<SplashImage>> getSplash();
}
