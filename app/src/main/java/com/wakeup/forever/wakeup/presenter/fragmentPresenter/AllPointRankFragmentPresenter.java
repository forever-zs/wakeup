package com.wakeup.forever.wakeup.presenter.fragmentPresenter;

import android.util.Log;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.UserDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.UserPoint;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.fragment.AllPointRankFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;

/**
 * Created by forever on 2016/9/10.
 */
public class AllPointRankFragmentPresenter extends BeamBasePresenter<AllPointRankFragment> {
    private boolean isLoadMore=true;
    public void refreshData() {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("limit", 10);
        queryMap.put("offset", 0);
        UserDataManager.getInstance().allPointRank(queryMap, new Subscriber<HttpResult<ArrayList<UserPoint>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().stopRefresh();
                Log.e("zs", e.getMessage());
                Log.e("zs", e.toString());
                ToastUtil.showText(GlobalConstant.ERROR_MESSAGE);
            }

            @Override
            public void onNext(HttpResult<ArrayList<UserPoint>> userPointHttpResult) {
                getView().stopRefresh();
                ArrayList<UserPoint> usrPoints = userPointHttpResult.getData();
                getView().onRefreshData(usrPoints);
            }
        });
    }

    public void loadMore(int offset) {
        LogUtil.e(isLoadMore+"");
        if(!isLoadMore){
            return ;
        }
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("limit", 10);
        queryMap.put("offset", offset);
        UserDataManager.getInstance().allPointRank(queryMap, new Subscriber<HttpResult<ArrayList<UserPoint>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showText(GlobalConstant.ERROR_MESSAGE);
            }

            @Override
            public void onNext(HttpResult<ArrayList<UserPoint>> userPointHttpResult) {
                ArrayList<UserPoint> usrPoints = userPointHttpResult.getData();
                if (usrPoints.size() == 0) {
                    ToastUtil.showText("没有更多了，亲");
                    isLoadMore=false;
                } else {
                    getView().onLoadMore(usrPoints);
                }

            }
        });
    }
}
