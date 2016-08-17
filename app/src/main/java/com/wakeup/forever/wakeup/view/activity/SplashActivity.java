package com.wakeup.forever.wakeup.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.presenter.activityPresenter.SplashActivityPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

@RequiresPresenter(SplashActivityPresenter.class)
public class SplashActivity extends BeamBaseActivity<SplashActivityPresenter> {

    @Bind(R.id.iv_splash)
    public ImageView ivSplash;
    @Bind(R.id.tv_tips)
    public TextView tvTips;
    @Bind(R.id.rl_root)
    public RelativeLayout rlRoot;
    @Bind(R.id.iv_wakeup)
    public ImageView ivWakeup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        getPresenter().startAnim();
    }

    public void startMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void showView(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .error(R.drawable.wakeup)
                .crossFade()
                .into(ivSplash);
        ivWakeup.setVisibility(View.INVISIBLE);
        getPresenter().startAdAnim();
    }


}
