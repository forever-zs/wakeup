package com.wakeup.forever.wakeup.model.service;

import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.TodayRank;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.model.bean.UserPoint;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by forever on 2016/8/19.
 */
public interface UserService {
    @GET("user/register.do")
    Observable<HttpResult<User>> register(@Query("phone") String phone, @Query("password") String password,@Query("code") String code);

    @GET("user/login.do")
    Observable<HttpResult<User>> login(@Query("phone") String phone,@Query("password") String password);

    @GET("user/signIn.do")
    Observable<HttpResult<TodayRank>> signIn(@Query("token") String token);

    @GET("user/getSignInfo.do")
    Observable<HttpResult<ArrayList<Long>>> getSignInfo(@Query("token") String token);

    @POST("user/updateUserInfo.do")
    Observable<HttpResult<User>> updateUserInfo(@Query("token") String token, @QueryMap Map<String,Object> user);

    @POST("user/getUserInfo.do")
    Observable<HttpResult<User>> getUserInfo(@Query("token") String token);

    @POST("user/password/updatePassword.do")
    Observable<HttpResult<User>> updatePassword(@Query("phone") String phone,@Query("password") String password,@Query("code") String code);

    @Multipart
    @POST("user/uploadHeadUrl.do")
    Observable<HttpResult<User>> uploadHeadUrl(@Query("token") String token, @Part("headImage"+"\";filename=\""+"image.jpg") RequestBody headImage);

    @POST("user/point/monthPointRank.do")
    Observable<HttpResult<ArrayList<UserPoint>>> monthPointRank(@QueryMap Map<String,Object> queryMap);

    @POST("user/point/allPointRank.do")
    Observable<HttpResult<ArrayList<UserPoint>>> allPointRank(@QueryMap Map<String,Object> queryMap);

    @GET("user/getCheckCode.do")
    Observable<HttpResult<String>> getCheckCode(@Query("phone") String phone);

    @POST("user/userInfoWithSign.do")
    Observable<HttpResult<UserPoint>> getUserInfoWithSign(@Query("token") String token);

}
