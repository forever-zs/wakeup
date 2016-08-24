package com.wakeup.forever.wakeup.model.service;

import com.squareup.okhttp.RequestBody;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.User;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by forever on 2016/8/19.
 */
public interface UserService {
    @GET("user/register.do")
    Observable<HttpResult<User>> register(@Query("phone") String phone,@Query("password") String password);

    @GET("user/login.do")
    Observable<HttpResult<User>> login(@Query("phone") String phone,@Query("password") String password);

    @POST("user/updateUserInfo.do")
    Observable<HttpResult<User>> updateUserInfo(@Query("token") String token, @QueryMap Map<String,Object> user);

    @POST("user/getUserInfo.do")
    Observable<HttpResult<User>> getUserInfo(@Query("token") String token);

    @Multipart
    @POST("user/uploadHeadUrl.do")
    Observable<HttpResult<User>> uploadHeadUrl(@Query("token") String token, @Part("headImage"+"\";filename=\""+"image.jpg")RequestBody headImage);


}
