package com.wakeup.forever.wakeup.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.base.BaseActivity;
import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.presenter.activityPresenter.PublishCommonShareActivityPresenter;
import com.wakeup.forever.wakeup.utils.GetImageUtils;
import com.wakeup.forever.wakeup.utils.SnackBarUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Manifest;

import butterknife.Bind;
import butterknife.ButterKnife;

@RequiresPresenter(PublishCommonShareActivityPresenter.class)
public class PublishCommonShareActivity extends BaseActivity<PublishCommonShareActivityPresenter> {

    @Bind(R.id.ib_back)
    ImageButton ibBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.ib_publish)
    ImageView ibPublish;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.et_text)
    EditText etText;
    @Bind(R.id.ll_text)
    LinearLayout llText;
    @Bind(R.id.iv_addPicture)
    ImageView ivAddPicture;
    @Bind(R.id.tl_publishCommonShare)
    RelativeLayout tlPublishCommonShare;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_common_share);
        ButterKnife.bind(this);
        ActivityManager.addActivity(this);
        initView();
        getPresenter().initData();
    }

    private void initView() {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, GetImageUtils.PHOTO_PICK);
            }
        });

        ibPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().publishCommonShare();
            }
        });
    }


    /*public void showImage(File tempFile) {
        LogUtil.e(tempFile.getAbsolutePath());
        Glide.with(this)
                .load(tempFile)
                .error(R.drawable.add_blue)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivAddPicture);
    }*/

    public ImageView getIvAddPicture() {
        return ivAddPicture;
    }

    public void showImage(Bitmap bitmap) {
        ivAddPicture.setImageBitmap(bitmap);
    }

    public Map<String, Object> getAllDate() {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        String content = etText.getText().toString();
        if (content.isEmpty()) {
            showSnackBar("请先填写内容");
            return null;
        }
        queryMap.put("content", content);
        return queryMap;
    }

    public void showSnackBar(String text) {
        SnackBarUtil.showText(tlPublishCommonShare, text);
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("发布中...");
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if(progressDialog!=null){
            progressDialog.dismiss();
        }

    }


}
