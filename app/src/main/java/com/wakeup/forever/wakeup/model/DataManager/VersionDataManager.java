package com.wakeup.forever.wakeup.model.DataManager;

import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.VersionUpdate;
import com.wakeup.forever.wakeup.model.service.VersionService;
import com.wakeup.forever.wakeup.utils.RetrofitUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by forever on 2016/9/18.
 */
public class VersionDataManager {

    private VersionService versionService;

    private static VersionDataManager versionDataManager;

    private VersionDataManager(){
        versionService= RetrofitUtil.getRetrofit().create(VersionService.class);
    }

    public static VersionDataManager getInstance(){
        if(versionDataManager==null){
            versionDataManager=new VersionDataManager();
            return versionDataManager;
        }
        else{
            return versionDataManager;
        }
    }

    public void getNewVersion(Subscriber<HttpResult<VersionUpdate>> subscriber){
        versionService.getNewVersion()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
