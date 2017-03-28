package com.wakeup.forever.wakeup.presenter.activityPresenter;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.app.App;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.UserCacheManager;
import com.wakeup.forever.wakeup.model.DataManager.UserDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.utils.PrefUtils;
import com.wakeup.forever.wakeup.view.activity.MainActivity;

/**
 * Created by forever on 2016/8/4.
 */
public class MainActivityPresenter extends BeamBasePresenter<MainActivity> {

    public void initData() {
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        if(token.isEmpty()){
            return;
        }
        User userTemp = UserCacheManager.getInstance(getView().getBaseContext()).getUser();
        if(userTemp!=null){
            getView().showUserInfo(userTemp);
        }
        UserDataManager.getInstance().getUserInfo(new BaseSubscriber<HttpResult<User>>(getView().getBaseContext()) {
            @Override
            public void onSuccess(HttpResult<User> userHttpResult) {
                if (userHttpResult.getResultCode() == 200) {
                    User user=userHttpResult.getData();
                    getView().showUserInfo(user);
                    UserCacheManager.getInstance(getView().getBaseContext()).updateUser(userHttpResult.getData());
                } else {

                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
