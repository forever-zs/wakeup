package com.wakeup.forever.wakeup.model.DataManager;

import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.SplashImage;
import com.wakeup.forever.wakeup.model.service.ImageService;
import com.wakeup.forever.wakeup.utils.RetrofitUtil;

import retrofit2.Retrofit;
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
        retrofit= RetrofitUtil.getRetrofit();
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
