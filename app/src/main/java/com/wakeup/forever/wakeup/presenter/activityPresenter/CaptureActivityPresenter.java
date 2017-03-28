package com.wakeup.forever.wakeup.presenter.activityPresenter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.model.DataManager.ShopDataManager;
import com.wakeup.forever.wakeup.model.bean.ExchangeRecord;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.ShopItem;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.view.activity.CaptureActivity;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by forever on 2016/12/7.
 */

public class CaptureActivityPresenter extends BeamBasePresenter<CaptureActivity> {

    public void resumeActivity(String jsonResult){
        Gson gson=new Gson();

        try{
            final ExchangeRecord exchangeRecord=gson.fromJson(jsonResult, ExchangeRecord.class);
            ShopDataManager.getInstance().resumeExchangeRecord(exchangeRecord.getShopId(),exchangeRecord.getUserPhone(), exchangeRecord.getCode(), new Subscriber<HttpResult<ShopItem>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    getView().onBadNetWork();
                    try {
                        LogUtil.e(((HttpException)e).response().errorBody().string());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void onNext(HttpResult<ShopItem> stringHttpResult) {
                    if(stringHttpResult.getResultCode()==200){
                        getView().exchangeSuccess(stringHttpResult.getData());
                    }
                    else{
                        getView().exchangeFailure(stringHttpResult.getMessage());
                    }
                }
            });
        }
        catch (JsonSyntaxException e){
            getView().exchangeFailure("二维码格式不正确");
        }
        catch (Exception e){
            getView().exchangeFailure("二维码格式不正确");
        }


    }
}
