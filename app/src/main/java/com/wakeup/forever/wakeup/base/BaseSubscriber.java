package com.wakeup.forever.wakeup.base;

import android.content.Context;
import android.content.Intent;

import com.wakeup.forever.wakeup.app.App;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.utils.PrefUtils;
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
            String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
            if(token.isEmpty()){
                ToastUtil.showText("请先登陆才能体验哦");
            }
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
