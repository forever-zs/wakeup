package com.wakeup.forever.wakeup.presenter.fragmentPresenter;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.ShareCacheManager;
import com.wakeup.forever.wakeup.model.DataManager.ShareDataManager;
import com.wakeup.forever.wakeup.model.bean.CommonShare;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.fragment.CommonShareFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by forever on 2016/8/26.
 */
public class CommonShareFragmentPresenter extends BeamBasePresenter<CommonShareFragment> {

    public void refreshData(){
        Map<String,Object> requestMap=new HashMap<String, Object>();
        requestMap.put("limit",5);
        ShareDataManager.getInstance().getCommonShare(requestMap, new BaseSubscriber<HttpResult<ArrayList<CommonShare>>>(getView().getContext()) {
            @Override
            public void onSuccess(HttpResult<ArrayList<CommonShare>> arrayListHttpResult) {
                ArrayList<CommonShare> commonShares=arrayListHttpResult.getData();
                getView().getCommonShareList().clear();
                getView().getCommonShareList().addAll(commonShares);
                getView().refreshData();
                ShareCacheManager.getInstance(getView().getContext()).saveCommonShareList(commonShares);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().refreshData();
            }
        });
    }

    public void loadMore(){
        Map<String,Object> requestMap=new HashMap<String, Object>();
        requestMap.put("limit",5);
        requestMap.put("offset",getView().getCommonShareList().size());
        ShareDataManager.getInstance().getCommonShare(requestMap, new BaseSubscriber<HttpResult<ArrayList<CommonShare>>>(getView().getContext()) {
            @Override
            public void onSuccess(HttpResult<ArrayList<CommonShare>> arrayListHttpResult) {
                ArrayList<CommonShare> commonShares=arrayListHttpResult.getData();
                if(commonShares.size()==0){
                    ToastUtil.showText("没有更多了，亲");
                    return;
                }
                getView().getCommonShareList().addAll(commonShares);
                getView().refreshData();
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().refreshData();
                ToastUtil.showText(GlobalConstant.ERROR_MESSAGE);
            }
        });
    }

    public void initDataFromCache(){
        ArrayList<CommonShare> commonShares=ShareCacheManager.getInstance(getView().getContext()).getLastCommonShareList();
        if(commonShares.size()!=0){
            getView().getCommonShareList().clear();
            getView().getCommonShareList().addAll(commonShares);
            getView().refreshData();
        }

    }

    public void favourite(int commonShareLike){
        ShareDataManager.getInstance().favourite(commonShareLike, new Subscriber<HttpResult<CommonShare>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showText(GlobalConstant.ERROR_MESSAGE);
            }

            @Override
            public void onNext(HttpResult<CommonShare> commonShareHttpResult) {
                    if(commonShareHttpResult.getResultCode()==200){
                        ToastUtil.showText("点赞成功");
                    }
                    else{
                        ToastUtil.showText(commonShareHttpResult.getMessage());
                    }
            }
        });
    }

    public void sendComment(String comment,int commonShareId){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("commonShareId",commonShareId);
        map.put("comment",comment);
        ShareDataManager.getInstance().comment(map, new Subscriber<HttpResult<CommonShare>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showText(e.getMessage());
            }

            @Override
            public void onNext(HttpResult<CommonShare> commonShareHttpResult) {
                    ToastUtil.showText(commonShareHttpResult.getMessage());
            }
        });
    }
}
