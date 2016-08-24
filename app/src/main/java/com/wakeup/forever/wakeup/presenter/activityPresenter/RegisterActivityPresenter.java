package com.wakeup.forever.wakeup.presenter.activityPresenter;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.model.DataManager.UserDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.view.activity.RegisterActivity;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import rx.Subscriber;

/**
 * Created by forever on 2016/8/21.
 */
public class RegisterActivityPresenter extends BeamBasePresenter<RegisterActivity> {

    private RegisterActivity registerActivity;

    @Override
    protected void onCreate(@NonNull RegisterActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        registerActivity=getView();
        initMob();
    }

    private void initMob() {
        EventHandler eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if(!registerActivity.checkProgressDialogIsNull()){
                            registerActivity.dismissProgressDialog();
                        }
                        register();
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){

                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                    if(!registerActivity.checkProgressDialogIsNull()){
                        registerActivity.dismissProgressDialog();
                    }
                    registerActivity.showSnackBar("验证码错误");
                }
            }
        };
        SMSSDK.registerEventHandler(eh);
    }

    public void getCode(){
        //发送短信验证码
        SMSSDK.getVerificationCode("86",registerActivity.getInputPhone());

        //改变button的状态
        new Thread(){
            @Override
            public void run() {
                super.run();
                for(int i=60;i>=0;i--){
                    try {
                        Message message=new Message();
                        message.what=RegisterActivity.COUNTDOWN;
                        Bundle data=new Bundle();
                        data.putInt("count",i);
                        message.setData(data);
                        registerActivity.handler.sendMessage(message);
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void validateCode(){
        SMSSDK.submitVerificationCode("86",registerActivity.getInputPhone(),registerActivity.getInputCode());
    }

    public void register(){
        String phone=registerActivity.getInputPhone();
        String password=registerActivity.getInputPassword();
        UserDataManager userDataManager=UserDataManager.getInstance();
        userDataManager.register(phone, password, new Subscriber<HttpResult<User>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HttpResult<User> userHttpResult) {
                if(userHttpResult.getResultCode()==200){
                    registerActivity.showSnackBar("注册成功");
                }
                else{
                    registerActivity.showSnackBar("该账号已被注册");
                }
            }
        });
    }
}
