package com.wakeup.forever.wakeup.model.DataManager;

import com.wakeup.forever.wakeup.app.App;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.TodayRank;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.model.bean.UserPoint;
import com.wakeup.forever.wakeup.model.service.UserService;
import com.wakeup.forever.wakeup.utils.PrefUtils;
import com.wakeup.forever.wakeup.utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
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

    public void register(String name,String password,String code,Subscriber<HttpResult<User>> subscriber){
        userService.register(name,password,code)
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

    public void uploadHeadUrl(RequestBody requestBody, BaseSubscriber<HttpResult<User>> subscriber){
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        userService.uploadHeadUrl(token,requestBody)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void updateUserInfo(Map<String,Object> user, BaseSubscriber<HttpResult<User>> subscriber){
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        userService.updateUserInfo(token,user)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public void signIn(BaseSubscriber<HttpResult<TodayRank>> subscriber){
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        userService.signIn(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getSignInfo(BaseSubscriber<HttpResult<ArrayList<Long>>> subscriber){
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        userService.getSignInfo(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getSignCount(Subscriber<HttpResult<ArrayList<Long>>> subscriber){
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        userService.getSignInfo(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void updatePassword(String phone,String password,String code,Subscriber<HttpResult<User>> subscriber){
        userService.updatePassword(phone,password,code)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void monthPointRank(Map<String,Object> queryMap,Subscriber<HttpResult<ArrayList<UserPoint>>> subscriber){
        userService.monthPointRank(queryMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void allPointRank(Map<String,Object> queryMap,Subscriber<HttpResult<ArrayList<UserPoint>>> subscriber){
        userService.allPointRank(queryMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getCheckCode(String phone,Subscriber<HttpResult<String>> subscriber){
        userService.getCheckCode(phone)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getUserInfoWithSign(BaseSubscriber<HttpResult<UserPoint>> baseSubscriber){
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        userService.getUserInfoWithSign(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseSubscriber);
    }


}
