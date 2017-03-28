package com.wakeup.forever.wakeup.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.base.BaseActivity;
import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.presenter.activityPresenter.SplashActivityPresenter;
import com.wakeup.forever.wakeup.utils.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

@RequiresPresenter(SplashActivityPresenter.class)
public class SplashActivity extends BaseActivity<SplashActivityPresenter> {

    @Bind(R.id.tv_tips)
    public TextView tvTips;
    @Bind(R.id.rl_root)
    public RelativeLayout rlRoot;
    @Bind(R.id.iv_wakeup)
    public ImageView ivWakeup;
    @Bind(R.id.iv_splash)
    public ImageView ivSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        getPresenter().startAnim();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void startMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void showView(String imageUrl) {
        LogUtil.e("showView");
        Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.guide1)
                .crossFade()
                .into(new GlideDrawableImageViewTarget(ivSplash) {
                          @Override
                          public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                              super.onResourceReady(resource, animation);
                              ivWakeup.setVisibility(View.INVISIBLE);
                              getPresenter().startAdAnim();
                          }
                      }
                );
    }

    public void startGuideActivity(){
        Intent i = new Intent(this, GuideActivity.class);
        startActivity(i);
        finish();
    }



}
