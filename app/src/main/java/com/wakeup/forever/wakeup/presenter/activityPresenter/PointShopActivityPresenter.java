package com.wakeup.forever.wakeup.presenter.activityPresenter;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.model.DataManager.ShopDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.ShopItem;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.view.activity.PointShopActivity;

import java.util.ArrayList;
import java.util.LinkedList;

import rx.Subscriber;

/**
 * Created by forever on 2016/11/21.
 */

public class PointShopActivityPresenter extends BeamBasePresenter<PointShopActivity> {
    public void getAllShops(){
        ShopDataManager.getInstance().getAllShops(new Subscriber<HttpResult<LinkedList<ShopItem>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().stopRefresh();
                LogUtil.e(e.getMessage());
                getView().onGetAllShopFail("请检查网络连接");
            }

            @Override
            public void onNext(HttpResult<LinkedList<ShopItem>> arrayListHttpResult) {
                getView().stopRefresh();
                if(arrayListHttpResult.getResultCode()==200){
                    getView().onGetAllShopSuccess(arrayListHttpResult.getData());
                }
                else{
                    getView().onGetAllShopFail(arrayListHttpResult.getMessage());
                }
            }
        });
    }
}
