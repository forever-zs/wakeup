package com.wakeup.forever.wakeup.presenter.fragmentPresenter;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.model.DataManager.ShareDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.Share;
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

    public void initData(){
        Map<String,Object> query=new HashMap<String, Object>();
        ShareDataManager.getInstance().getShare(query, new Subscriber<HttpResult<ArrayList<Share>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showText(e.getMessage());
            }

            @Override
            public void onNext(HttpResult<ArrayList<Share>> arrayListHttpResult) {
                if(arrayListHttpResult.getResultCode()==200){
                    getView().showShareList(arrayListHttpResult.getData());
                }
                ToastUtil.showText(arrayListHttpResult.getMessage());
            }
        });
    }

}
