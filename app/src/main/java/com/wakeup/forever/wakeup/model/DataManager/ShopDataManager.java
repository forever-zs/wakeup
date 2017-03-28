package com.wakeup.forever.wakeup.model.DataManager;

import com.wakeup.forever.wakeup.app.App;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.bean.ExchangeRecord;
import com.wakeup.forever.wakeup.model.bean.ExchangeRecordForUser;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.ShopItem;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.model.service.ShopService;
import com.wakeup.forever.wakeup.model.service.UserService;
import com.wakeup.forever.wakeup.utils.PrefUtils;
import com.wakeup.forever.wakeup.utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.LinkedList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by forever on 2016/11/26.
 */

public class ShopDataManager {
    private ShopService shopService;
    private static ShopDataManager shopDataManager;

    private ShopDataManager(){
        shopService= RetrofitUtil.getRetrofit().create(ShopService.class);
    }

    public static ShopDataManager getInstance(){
        if(shopDataManager==null){
            shopDataManager=new ShopDataManager();
            return shopDataManager;
        }
        else{
            return shopDataManager;
        }
    }

    public void getAllShops(Subscriber<HttpResult<LinkedList<ShopItem>>> subscriber){
        shopService.getAllShops()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void exchangeShop(int shopId,BaseSubscriber<HttpResult<ExchangeRecord>> subscriber){
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        shopService.exchangeShop(token,shopId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getExchangeRecord(Subscriber<HttpResult<LinkedList<ExchangeRecordForUser>>> subscriber){
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        shopService.getExchangeRecord(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void resumeExchangeRecord(int shopId,String userPhone,String code,Subscriber<HttpResult<ShopItem>> subscriber){
        shopService.resumeExchangeRecord(shopId,userPhone,code)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
