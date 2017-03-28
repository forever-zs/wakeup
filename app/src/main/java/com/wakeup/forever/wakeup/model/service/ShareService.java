package com.wakeup.forever.wakeup.model.service;

import com.wakeup.forever.wakeup.model.bean.CommonShare;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.Share;

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
 * Created by forever on 2016/8/26.
 */
public interface ShareService {
    @GET("share/getShare.do")
    Observable<HttpResult<ArrayList<Share>>> getShare(@QueryMap Map<String,Object> query);

    @GET("commonShare/getCommonShare.do")
    Observable<HttpResult<ArrayList<CommonShare>>> getCommonShare(@Query("token") String token, @QueryMap Map<String,Object> query);

    @GET("commonShare/favourite.do")
    Observable<HttpResult<CommonShare>> favourite(@Query("token") String token,@Query("commonShareId") int commonShareId);

    @GET("commonShare/comment.do")
    Observable<HttpResult<CommonShare>> comment(@QueryMap Map<String,Object> query);

    @Multipart
    @POST("commonShare/publishCommonShare.do")
    Observable<HttpResult<String>> publishCommonShare(@Query("token") String token,@QueryMap Map<String,Object> query,@Part("image"+"\";filename=\""+"image.jpg") RequestBody image);

    @POST("commonShare/publishCommonShareWithoutImage.do")
    Observable<HttpResult<String>> publishCommonShareWithoutImage(@Query("token") String token,@QueryMap Map<String,Object> query);
}
