package com.wakeup.forever.wakeup.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.base.BaseActivity;
import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.presenter.activityPresenter.FindPasswordActivityPresenter;
import com.wakeup.forever.wakeup.utils.CheckUtil;
import com.wakeup.forever.wakeup.utils.UiUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

@RequiresPresenter(FindPasswordActivityPresenter.class)
public class FindPasswordActivity extends BaseActivity<FindPasswordActivityPresenter> {

    @Bind(R.id.input_phone)
    EditText inputPhone;
    @Bind(R.id.input_password)
    EditText inputPassword;
    @Bind(R.id.input_code)
    EditText inputCode;
    @Bind(R.id.btn_getCode)
    AppCompatButton btnGetCode;
    @Bind(R.id.btn_update)
    AppCompatButton btnUpdate;
    @Bind(R.id.tv_loginLink)
    TextView tvLoginLink;
    @Bind(R.id.ll_updatePassword)
    LinearLayout llUpdatePassword;

    public static int COUNTDOWN = 1;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == RegisterActivity.COUNTDOWN) {
                int count = msg.getData().getInt("count");
                btnGetCode.setText(count + "");
                if (count == 0) {
                    btnGetCode.setClickable(true);
                    btnGetCode.setBackgroundColor(Color.GREEN);
                    btnGetCode.setText("获取验证码");
                } else {
                    btnGetCode.setClickable(false);
                    btnGetCode.setBackgroundColor(getResources().getColor(R.color.mainColor));
                }
            }
        }
    };


    FindPasswordActivityPresenter findPasswordActivityPresenter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityManager.addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        UiUtil.immerseStatusBar(this);
        findPasswordActivityPresenter=getPresenter();
        initView();
    }

    private void initView() {
        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPasswordActivityPresenter.getCode(getInputPhone());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!CheckUtil.checkPhone(inputPhone.getText().toString())) {
                    showSnackBar("手机号格式不正确");
                    dismissProgressDialog();
                }
                else if(!CheckUtil.checkPassword(inputPassword.getText().toString())){
                    showSnackBar("密码必须为6~20字母与数字的组合");
                    dismissProgressDialog();
                }
                else{
                    findPasswordActivityPresenter.updatePassword(getInputPhone(),getInputPassword(),getInputCode());
                }

            }
        });

        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToLoginAty();
            }
        });
    }

    public String getInputPhone() {
        return inputPhone.getText().toString();
    }

    public String getInputPassword(){
        return  inputPassword.getText().toString();
    }
    public String getInputCode() {
        return inputCode.getText().toString();
    }
    public void showSnackBar(String text){
        Snackbar.make(llUpdatePassword,text,Snackbar.LENGTH_SHORT)
                .setAction("好的", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setActionTextColor(getResources().getColor(R.color.mainColor))
                .show();
    }

    public void jumpToLoginAty(){
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("修改中...");
        progressDialog.show();

    }

    public void dismissProgressDialog() {
        if(progressDialog!=null){
            progressDialog.dismiss();
        }

    }
}
