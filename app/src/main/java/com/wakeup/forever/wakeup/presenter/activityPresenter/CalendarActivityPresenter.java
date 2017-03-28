package com.wakeup.forever.wakeup.presenter.activityPresenter;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.UserDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.activity.CalendarActivity;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by forever on 2016/9/1.
 */
public class CalendarActivityPresenter extends BeamBasePresenter<CalendarActivity> {
    public void initData() {
        getView().showProgressDialog();
        UserDataManager.getInstance().getSignInfo(new BaseSubscriber<HttpResult<ArrayList<Long>>>(getView()) {
            @Override
            public void onSuccess(HttpResult<ArrayList<Long>> arrayListHttpResult) {
                if (arrayListHttpResult.getResultCode() == 200) {
                    ArrayList<Calendar> calendarArrayList=new ArrayList<Calendar>();
                    for (Long day : arrayListHttpResult.getData()) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(day);
                        calendarArrayList.add(calendar);
                    }
                    getView().onGetSignDays(calendarArrayList);
                }
                else {
                    ToastUtil.showText(GlobalConstant.ERROR_MESSAGE);
                }
                getView().dismissProgressDialog();
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e.getMessage());
                getView().dismissProgressDialog();
                ToastUtil.showText("网络出了点问题呢");
            }
        });
    }
}
