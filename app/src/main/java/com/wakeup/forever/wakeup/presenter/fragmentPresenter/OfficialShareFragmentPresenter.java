package com.wakeup.forever.wakeup.presenter.fragmentPresenter;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.model.DataManager.ShareCacheManager;
import com.wakeup.forever.wakeup.model.DataManager.ShareDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.Share;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.fragment.OfficialShareFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by forever on 2016/8/26.
 */
public class OfficialShareFragmentPresenter extends BeamBasePresenter<OfficialShareFragment> {

    public void initData(){
        ArrayList<Share> shareList=ShareCacheManager.getInstance(getView().getContext()).getLastShareList();
        if(shareList.size()==10){
            getView().getShareList().clear();
            getView().getShareList().addAll(shareList);
            getView().showShareList();
        }
        else{
            refreshData();
        }
    }

    public void refreshData(){
        Map<String,Object> query=new HashMap<String, Object>();
        query.put("limit",10);
        ShareDataManager.getInstance().getShare(query, new Subscriber<HttpResult<ArrayList<Share>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().stopRefresh();
            }

            @Override
            public void onNext(HttpResult<ArrayList<Share>> arrayListHttpResult) {
                if(arrayListHttpResult.getResultCode()==200){
                    getView().getShareList().clear();
                    getView().getShareList().addAll(arrayListHttpResult.getData());
                    getView().showShareList();
                    ShareCacheManager.getInstance(getView().getContext()).saveShareList(arrayListHttpResult.getData());
                }
                else {
                    ToastUtil.showText("网络出了点问题呢");
                }
                getView().stopRefresh();
            }
        });
    }

    public void loadMore() {
        Map<String,Object> query=new HashMap<String, Object>();
        query.put("limit",10);
        query.put("offset",getView().getShareList().size());
        ShareDataManager.getInstance().getShare(query, new Subscriber<HttpResult<ArrayList<Share>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().showSnackBar("床君好像又崩潰了,親");
            }

            @Override
            public void onNext(HttpResult<ArrayList<Share>> arrayListHttpResult) {
                if(arrayListHttpResult.getResultCode()==200){
                    if(arrayListHttpResult.getData().size()==0){
                        ToastUtil.showText("沒有更多了，親");
                    }
                    else{
                        getView().getShareList().addAll(arrayListHttpResult.getData());
                        getView().showShareList();
                    }
                }
            }
        });
    }
}
