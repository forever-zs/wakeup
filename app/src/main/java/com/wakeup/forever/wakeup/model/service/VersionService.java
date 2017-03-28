package com.wakeup.forever.wakeup.model.service;

import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.VersionUpdate;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by forever on 2016/9/18.
 */
public interface VersionService {

    @GET("version/update.do")
    Observable<HttpResult<VersionUpdate>> getNewVersion();
}
