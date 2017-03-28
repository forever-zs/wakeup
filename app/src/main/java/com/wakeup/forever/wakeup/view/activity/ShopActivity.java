package com.wakeup.forever.wakeup.view.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.base.BaseActivity;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.bean.ExchangeRecord;
import com.wakeup.forever.wakeup.model.bean.ShopItem;
import com.wakeup.forever.wakeup.presenter.activityPresenter.ShopActivityPresenter;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

@RequiresPresenter(ShopActivityPresenter.class)
public class ShopActivity extends BaseActivity<ShopActivityPresenter> {

    @Bind(R.id.wb_shopDisplay)
    WebView wbShopDisplay;
    @Bind(R.id.btn_exchange)
    Button btnExchange;
    @Bind(R.id.activity_shop)
    LinearLayout activityShop;
    @Bind(R.id.pb_load)
    ProgressBar pbLoad;
    @Bind(R.id.tv_point)
    TextView tvPoint;
    private TextView tvImgDesc;
    private ImageView ivCode;
    private LinearLayout layoutEditText;

    private ShopItem shopItem;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        shopItem = (ShopItem) getIntent().getExtras().getSerializable(GlobalConstant.SHOP_ITMP);
        getPresenter().loadUrl(wbShopDisplay, shopItem.getShopUrl());
    }

    private void initView() {
        btnExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopItem != null) {
                    getPresenter().exchangeShop(shopItem.getId());
                } else {
                    ToastUtil.showText("系统忙，请稍候再试");
                }
            }
        });
        tvPoint.setText(shopItem.getPoint()+"积分");
    }

    public void onExchangeSuccess(ExchangeRecord exchangeRecord) {
        showCodeDialog(exchangeRecord);
    }

    private void showCodeDialog(ExchangeRecord exchangeRecord) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(R.layout.dialog_show_image)
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
        tvImgDesc = (TextView) dialog.findViewById(R.id.tv_imgDesc);
        ivCode = (ImageView) dialog.findViewById(R.id.iv_code);
        Gson gson = new Gson();
        Bitmap bitmap = CodeUtils.createImage(gson.toJson(exchangeRecord), 400, 400, null);
        ivCode.setImageBitmap(bitmap);
        LogUtil.e(gson.toJson(exchangeRecord));
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("积分验证中...");
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void setLoadingProgress(int progress, boolean finsh) {
        pbLoad.setProgress(progress);
        if (finsh) {
            pbLoad.setVisibility(ProgressBar.GONE);
        }
    }


}
