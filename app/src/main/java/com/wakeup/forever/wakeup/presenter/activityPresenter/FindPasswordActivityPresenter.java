package com.wakeup.forever.wakeup.presenter.activityPresenter;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.UserDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.activity.FindPasswordActivity;
import com.wakeup.forever.wakeup.view.activity.RegisterActivity;

import rx.Subscriber;

/**
 * Created by forever on 2016/9/7.
 */
public class FindPasswordActivityPresenter extends BeamBasePresenter<FindPasswordActivity> {

    @Override
    protected void onCreate(@NonNull FindPasswordActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
       // initMob();
    }

   /* private void initMob() {
        EventHandler eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            getView().dismissProgressDialog();
                        updatePassword();
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){

                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                        getView().dismissProgressDialog();
                    getView().showSnackBar("验证码错误");
                }
            }
        };
        SMSSDK.registerEventHandler(eh);
    }*/

    public void getCode(String phone){
        UserDataManager userDataManager=UserDataManager.getInstance();
        userDataManager.getCheckCode(phone, new Subscriber<HttpResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showText(GlobalConstant.ERROR_MESSAGE);
            }

            @Override
            public void onNext(HttpResult<String> stringHttpResult) {
                ToastUtil.showText(stringHttpResult.getMessage());
            }
        });
        //改变button的状态
        new Thread(){
            @Override
            public void run() {
                super.run();
                for(int i=60;i>=0;i--){
                    try {
                        Message message=new Message();
                        message.what= RegisterActivity.COUNTDOWN;
                        Bundle data=new Bundle();
                        data.putInt("count",i);
                        message.setData(data);
                        getView().handler.sendMessage(message);
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void validateCode(){
        //SMSSDK.submitVerificationCode("86",getView().getInputPhone(),getView().getInputCode());
    }

    public void updatePassword(String phone,String password,String code){
        getView().showProgressDialog();
        UserDataManager userDataManager=UserDataManager.getInstance();
        userDataManager.updatePassword(phone, password,code, new Subscriber<HttpResult<User>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().dismissProgressDialog();
                ToastUtil.showText(GlobalConstant.ERROR_MESSAGE);
            }

            @Override
            public void onNext(HttpResult<User> userHttpResult) {
                getView().dismissProgressDialog();
                if(userHttpResult.getResultCode()==200){
                    ToastUtil.showText("修改成功");
                    getView().jumpToLoginAty();
                }
                else{
                    getView().showSnackBar(userHttpResult.getMessage());
                }
            }
        });
    }
}
