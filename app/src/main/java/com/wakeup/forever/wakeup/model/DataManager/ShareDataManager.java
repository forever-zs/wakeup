package com.wakeup.forever.wakeup.model.DataManager;

import com.wakeup.forever.wakeup.app.App;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.bean.CommonShare;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.Share;
import com.wakeup.forever.wakeup.model.service.ShareService;
import com.wakeup.forever.wakeup.utils.PrefUtils;
import com.wakeup.forever.wakeup.utils.RetrofitUtil;
import com.wakeup.forever.wakeup.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.QueryMap;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by forever on 2016/8/26.
 */
public class ShareDataManager {
    private ShareService shareService;

    private static ShareDataManager shareDataManager;

    private ShareDataManager(){
        shareService= RetrofitUtil.getRetrofit().create(ShareService.class);
    }

    public static ShareDataManager getInstance(){
        if(shareDataManager==null){
            shareDataManager=new ShareDataManager();
            return shareDataManager;
        }
        else{
            return shareDataManager;
        }
    }

    public void getShare(Map<String,Object> query, Subscriber<HttpResult<ArrayList<Share>>> subscriber){
        shareService.getShare(query)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getCommonShare(Map<String,Object> query, BaseSubscriber<HttpResult<ArrayList<CommonShare>>> subscriber){
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        shareService.getCommonShare(token,query)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void publishCommonShare(Map<String,Object> query, RequestBody image, BaseSubscriber<HttpResult<String>> subscriber){

        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");

        if(image!=null){
            shareService.publishCommonShare(token,query,image)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }
        else{
            shareService.publishCommonShareWithoutImage(token,query)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }

    }

    public void favourite(int commonShareId, Subscriber<HttpResult<CommonShare>> subscriber){
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        if(token.isEmpty()){
            ToastUtil.showText("请先登陆才能点赞哦");
            return ;
        }
        shareService.favourite(token,commonShareId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void comment(@QueryMap Map<String,Object> query, Subscriber<HttpResult<CommonShare>> subscriber){
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        if(token.isEmpty()){
            ToastUtil.showText("请先登陆才能评论哦");
            return ;
        }
        query.put("token",token);
        shareService.comment(query)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
