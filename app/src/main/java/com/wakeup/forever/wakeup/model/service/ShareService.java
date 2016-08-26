package com.wakeup.forever.wakeup.model.service;

import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.Share;

import java.util.ArrayList;
import java.util.Map;

import retrofit.http.GET;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by forever on 2016/8/26.
 */
public interface ShareService {
    @GET("share/getShare.do")
    Observable<HttpResult<ArrayList<Share>>> getShare(@QueryMap Map<String,Object> query);
}
