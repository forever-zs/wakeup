package com.wakeup.forever.wakeup.model.DataManager;

import com.wakeup.forever.wakeup.app.App;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.Student;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.model.service.LessonService;
import com.wakeup.forever.wakeup.utils.LessonRetrofitUtil;
import com.wakeup.forever.wakeup.utils.PrefUtils;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YZune on 2017/5/29.
 */

public class LessonDataManager {
    private LessonService lessonService;

    private static LessonDataManager lessonDataManager;

    private LessonDataManager(){
        lessonService = LessonRetrofitUtil.getRetrofit().create(LessonService.class);
    }

    public static LessonDataManager getInstance(){
        if(lessonDataManager==null){
            lessonDataManager=new LessonDataManager();
            return lessonDataManager;
        }
        else{
            return lessonDataManager;
        }
    }

    public void sdLogin(String user, String password ,Subscriber<HttpResult<Student>> subscriber){
        lessonService.sdLogin("1627406067", "android:7.1.1", "110", "80e7eccf6749669af9525b20d5639700", "110", "")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void ScheduleList(BaseSubscriber<HttpResult<ArrayList<Long>>> subscriber){
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        lessonService.ScheduleList(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
