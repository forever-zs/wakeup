package com.wakeup.forever.wakeup.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.wakeup.forever.wakeup.presenter.activityPresenter.LoginActivityPresenter;
import com.wakeup.forever.wakeup.utils.UiUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


@RequiresPresenter(LoginActivityPresenter.class)
public class LoginActivity extends BaseActivity<LoginActivityPresenter> {

    @Bind(R.id.ll_login)
    LinearLayout llLogin;
    @Bind(R.id.input_phoneLogin)
    EditText inputPhoneLogin;
    @Bind(R.id.input_password)
    EditText inputPassword;
    @Bind(R.id.btn_login)
    AppCompatButton btn_login;
    @Bind(R.id.tv_signUp)
    TextView tvSignUp;
    @Bind(R.id.tv_find)
    TextView tvFind;

    LoginActivityPresenter loginActivityPresenter;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityManager.addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UiUtil.immerseStatusBar(this);
        ButterKnife.bind(this);
        loginActivityPresenter = getPresenter();
        initView();
    }


    private void initView() {
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = inputPhoneLogin.getText().toString();
                String password = inputPassword.getText().toString();
                if (phone.isEmpty() || password.isEmpty()) {
                    showSnackBar("用户名或密码不能为空");
                } else {
                    loginActivityPresenter.login(phone, password);
                }
            }
        });
        tvFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,FindPasswordActivity.class));
                finish();
            }
        });
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("登录中...");
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    public void showSnackBar(String text) {
        Snackbar.make(llLogin, text, Snackbar.LENGTH_SHORT)
                .setAction("好的", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setActionTextColor(getResources().getColor(R.color.mainColor))
                .show();
    }

    public void loginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


}
