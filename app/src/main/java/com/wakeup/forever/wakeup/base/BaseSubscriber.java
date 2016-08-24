package com.wakeup.forever.wakeup.base;

import android.content.Context;
import android.content.Intent;

import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.activity.LoginActivity;

import java.lang.ref.WeakReference;

import rx.Subscriber;

/**
 * Created by forever on 2016/8/23.
 */
public abstract class BaseSubscriber<T extends HttpResult> extends Subscriber<T>{
    private WeakReference<Context> context;

    public BaseSubscriber(Context context) {
        this.context = new WeakReference<Context>(context);
    }

    public abstract void onSuccess(T t);

    @Override
    public void onNext(T t) {
        if(t.getResultCode()==401){
            tokenIsInvalid();
        }
        else{
            onSuccess(t);
        }
    }

    public void tokenIsInvalid(){
        ToastUtil.showText("登陆授权已过期，请重新登陆");
        ActivityManager.finishAll();
        context.get().startActivity(new Intent(context.get(), LoginActivity.class));
    }

}
