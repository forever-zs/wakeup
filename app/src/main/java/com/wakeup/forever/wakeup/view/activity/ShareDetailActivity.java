package com.wakeup.forever.wakeup.view.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.jude.beam.bijection.RequiresPresenter;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.base.BaseActivity;
import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.presenter.activityPresenter.ShareDetailActivityPresenter;
import com.wakeup.forever.wakeup.utils.UiUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

@RequiresPresenter(ShareDetailActivityPresenter.class)
public class ShareDetailActivity extends BaseActivity<ShareDetailActivityPresenter> {

    @Bind(R.id.iv_shareDetail)
    ImageView ivShareDetail;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.fab_share)
    FloatingActionButton fabShare;
    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityManager.addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_detail);
        ButterKnife.bind(this);
        UiUtil.immerseStatusBar(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    private void initView() {
        toolbar.setTitle("");
        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(ShareDetailActivity.this).setPlatform(SHARE_MEDIA.QQ)
                        .withText("不辜负每一个清晨")
                        .withTargetUrl("http://121.42.163.226:8080/wakeup/html/index.html")
                        .setCallback(umShareListener)
                        .share();
            }
        });
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {

            Toast.makeText(ShareDetailActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ShareDetailActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ShareDetailActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

}
