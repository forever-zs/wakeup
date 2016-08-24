package com.wakeup.forever.wakeup.model.DataManager;

import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.wakeup.forever.wakeup.app.App;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.model.service.UserService;
import com.wakeup.forever.wakeup.utils.PrefUtils;
import com.wakeup.forever.wakeup.utils.RetrofitUtil;

import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by forever on 2016/8/19.
 */
public class UserDataManager {

    private UserService userService;

    private static UserDataManager userDataManager;

    private UserDataManager(){
        userService= RetrofitUtil.getRetrofit().create(UserService.class);
    }

    public static UserDataManager getInstance(){
        if(userDataManager==null){
            userDataManager=new UserDataManager();
            return userDataManager;
        }
        else{
            return userDataManager;
        }
    }

    public void login(String name,String password,Subscriber<HttpResult<User>> subscriber){
        userService.login(name,password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void register(String name,String password,Subscriber<HttpResult<User>> subscriber){
        userService.register(name,password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getUserInfo(BaseSubscriber<HttpResult<User>> subscriber){
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        userService.getUserInfo(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void uploadHeadUrl(RequestBody requestBody,BaseSubscriber<HttpResult<User>> subscriber){
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        userService.uploadHeadUrl(token,requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void updateUserInfo(Map<String,Object> user, BaseSubscriber<HttpResult<User>> subscriber){
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        Log.e("zs",token);
        userService.updateUserInfo(token,user)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

}
