package com.wakeup.forever.wakeup.presenter.activityPresenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.UserDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.utils.PrefUtils;
import com.wakeup.forever.wakeup.view.activity.LoginActivity;

import rx.Subscriber;

/**
 * Created by forever on 2016/8/21.
 */


public class LoginActivityPresenter extends BeamBasePresenter<LoginActivity> {

    private LoginActivity loginActivity;

    public void login(String phone,String password){
        loginActivity.showProgressDialog();
        UserDataManager userDataManager=UserDataManager.getInstance();
        userDataManager.login(phone, password, new Subscriber<HttpResult<User>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                loginActivity.showSnackBar("未知错误,请检查网络");
            }

            @Override
            public void onNext(HttpResult<User> userHttpResult) {
                loginActivity.dismissProgressDialog();
                if(userHttpResult.getResultCode()==200){
                    loginActivity.showSnackBar("登陆成功");
                    PrefUtils.setString(loginActivity, GlobalConstant.TOKEN,userHttpResult.getData().getToken());
                    Log.e("zs",userHttpResult.getData().getToken());
                    loginActivity.loginSuccess();
                }
                else{
                    loginActivity.showSnackBar(userHttpResult.getMessage());
                }
            }
        });
    }

    @Override
    protected void onCreate(@NonNull LoginActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        loginActivity=getView();
    }
}
