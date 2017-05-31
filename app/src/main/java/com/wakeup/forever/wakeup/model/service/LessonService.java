package com.wakeup.forever.wakeup.model.service;

import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.Student;
import com.wakeup.forever.wakeup.model.bean.User;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by YZune on 2017/5/29.
 */

public interface LessonService {

    @POST("/university-facade/Murp/sdLogin")
    Observable<HttpResult<Student>> sdLogin(@Query("u") String user, @Query("tec") String androidVer, @Query("type") String unknowNum1, @Query("p") String password, @Query("ver") String ver, @Query("uuid") String uuid);

    @GET("/university-facade/Schedule/ScheduleList")
    Observable<HttpResult<ArrayList<Long>>> ScheduleList(@Query("token") String token);
}
