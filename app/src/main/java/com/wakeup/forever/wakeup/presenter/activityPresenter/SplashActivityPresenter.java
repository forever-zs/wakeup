package com.wakeup.forever.wakeup.presenter.activityPresenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

import com.jude.beam.bijection.Presenter;
import com.wakeup.forever.wakeup.app.App;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.GetPictureFormUrl;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.SplashImage;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.PrefUtils;
import com.wakeup.forever.wakeup.view.activity.SplashActivity;

import rx.Subscriber;

/**
 * Created by forever on 2016/8/1.
 */
public class SplashActivityPresenter extends Presenter<SplashActivity>{

    private SplashActivity splashActivity;
    @Override
    protected void onCreate(@NonNull SplashActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        splashActivity=getView();
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
                getView().startMainActivity();
            }

            @Override
            public void onNext(HttpResult<SplashImage> splashImageHttpResult) {
                getView().showView(splashImageHttpResult.getData().getImageUrl());
                //getView().startMainActivity();
            }
        });
    }

    public void startAnim(){
        AlphaAnimation alpha=new AlphaAnimation(0f,1f);
        alpha.setDuration(2000);
        splashActivity.ivWakeup.startAnimation(alpha);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean isFirstEntry=PrefUtils.getBoolean(App.getGlobalContext(), GlobalConstant.IS_FIRST_ENTRY,true);
                if(isFirstEntry){
                    getView().startGuideActivity();
                    PrefUtils.setBoolean(App.getGlobalContext(),GlobalConstant.IS_FIRST_ENTRY,false);
                }
                else{
                    //getView().startGuideActivity();
                    loadImage();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void startAdAnim(){
        AnimationSet set=new AnimationSet(false);
        ScaleAnimation scaleAnimation=new ScaleAnimation(0.95f,1f,0.95f,1f,Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation=new AlphaAnimation(0.5f,1f);
        //设置动画时间
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(false);
        alphaAnimation.setDuration(1500);
        //添加动画
        set.addAnimation(alphaAnimation);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splashActivity.startMainActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        splashActivity.ivSplash.startAnimation(set);
    }
}
