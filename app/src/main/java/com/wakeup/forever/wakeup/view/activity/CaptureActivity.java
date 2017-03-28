package com.wakeup.forever.wakeup.view.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.jude.beam.bijection.RequiresPresenter;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.base.BaseActivity;
import com.wakeup.forever.wakeup.model.bean.ExchangeRecord;
import com.wakeup.forever.wakeup.model.bean.ShopItem;
import com.wakeup.forever.wakeup.presenter.activityPresenter.CaptureActivityPresenter;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.ToastUtil;

@RequiresPresenter(CaptureActivityPresenter.class)
public class CaptureActivity extends BaseActivity<CaptureActivityPresenter> implements CodeUtils.AnalyzeCallback{

    public static boolean isOpen = false;

    private CaptureFragment captureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        initView();
        initData();
    }
    protected void initView() {
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.fragment_capture);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, captureFragment).commit();
    }

    public void initData() {
        captureFragment.setAnalyzeCallback(this);
    }


    @Override
    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
        //处理扫描结果（在界面上显示）
        LogUtil.e(result);
        getPresenter().resumeActivity(result);
    }


    @Override
    public void onAnalyzeFailed() {
       ToastUtil.showText("无法解析，请于活动主办方联系");
    }


    public void exchangeSuccess(ShopItem shopItem) {
        //显示签到成功的dialog
        if(shopItem!=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("兑换结果：");
            builder.setMessage(shopItem.getShopName()+"兑换成功");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("兑换结果：");
            builder.setMessage("未找到商品");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    public void exchangeFailure(String message) {
        //显示签到失败的dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("兑换结果：");
        builder.setMessage(message);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onBadNetWork() {
        //显示网络连接失败的dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("系统提示：");
        builder.setMessage("网络连接异常，请稍后重试。");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
