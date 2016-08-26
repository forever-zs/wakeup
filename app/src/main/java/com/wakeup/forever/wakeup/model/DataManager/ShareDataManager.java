package com.wakeup.forever.wakeup.model.DataManager;

import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.Share;
import com.wakeup.forever.wakeup.model.service.ShareService;
import com.wakeup.forever.wakeup.utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.Map;

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

}
