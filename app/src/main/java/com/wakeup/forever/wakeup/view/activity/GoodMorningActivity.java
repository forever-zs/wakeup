package com.wakeup.forever.wakeup.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.base.BaseActivity;
import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.presenter.fragmentPresenter.GoodMorningFragmentPresenter;
import butterknife.Bind;
import butterknife.ButterKnife;

@RequiresPresenter(GoodMorningFragmentPresenter.class)
public class GoodMorningActivity extends BaseActivity<GoodMorningFragmentPresenter> {

    @Bind(R.id.iv_goodMorning)
    public ImageView ivGoodMorning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
        setContentView(R.layout.activity_good_morning);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void showView(String imageUrl) {
        LogUtil.e("showView");
        Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.guide1)
                .crossFade()
                .into(new GlideDrawableImageViewTarget(ivGoodMorning) {
                          @Override
                          public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                              super.onResourceReady(resource, animation);
                          }
                      }
                );
    }
}
