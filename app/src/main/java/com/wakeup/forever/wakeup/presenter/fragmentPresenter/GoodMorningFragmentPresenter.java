package com.wakeup.forever.wakeup.presenter.fragmentPresenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.DataManager.GetPictureFormUrl;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.SplashImage;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.view.activity.GoodMorningActivity;

import rx.Subscriber;

/**
 * Created by YZune on 2017/5/28.
 */

public class GoodMorningFragmentPresenter extends BeamBasePresenter<GoodMorningActivity> {
    private GoodMorningActivity goodMorningActivity;
    @Override
    protected void onCreate(@NonNull GoodMorningActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        goodMorningActivity=getView();
        loadImage();
    }

    public void loadImage(){
        GetPictureFormUrl.getInstance().getImage(new Subscriber<HttpResult<SplashImage>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("onError");
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onNext(HttpResult<SplashImage> splashImageHttpResult) {
                getView().showView(splashImageHttpResult.getData().getImageUrl());
                //getView().startMainActivity();
            }
        });
    }
}
