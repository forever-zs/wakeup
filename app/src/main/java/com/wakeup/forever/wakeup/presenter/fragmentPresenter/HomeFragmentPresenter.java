package com.wakeup.forever.wakeup.presenter.fragmentPresenter;

import android.util.Log;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.UserCacheManager;
import com.wakeup.forever.wakeup.model.DataManager.UserDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.view.fragment.HomeFragment;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by forever on 2016/8/17.
 */

public class HomeFragmentPresenter extends BeamBasePresenter<HomeFragment> {

    public void initData() {
        User userTemp = UserCacheManager.getUser();
        if(userTemp!=null){
            Log.e("zs",userTemp.getId()+"Id");
            getView().showUserInfo(userTemp);
        }
        List<User> users=DataSupport.findAll(User.class);
        Log.e("zs",users.size()+"个");
        UserDataManager.getInstance().getUserInfo(new BaseSubscriber<HttpResult<User>>(getView().getActivity()) {
            @Override
            public void onSuccess(HttpResult<User> userHttpResult) {
                if (userHttpResult.getResultCode() == 200) {
                    User user=userHttpResult.getData();
                    getView().showUserInfo(user);
                    getView().setHeadClickable(false);
                    UserCacheManager.saveUser(userHttpResult.getData());
                } else {
                    getView().showSnackBar(GlobalConstant.ERROR_MESSAGE);
                    getView().setHeadClickable(true);
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().setHeadClickable(true);
                getView().showSnackBar(GlobalConstant.ERROR_MESSAGE);
            }
        });
    }
}
