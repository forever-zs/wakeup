package com.wakeup.forever.wakeup.model.service;

import com.wakeup.forever.wakeup.model.bean.Emotion;
import com.wakeup.forever.wakeup.model.bean.FaceAnalyse;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.User;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by forever on 2016/11/26.
 */

public interface FaceService {
    @Multipart
    @POST("facepp/v3/detect")
    Observable<FaceAnalyse> addEmotion(@Part("image_file"+"\";filename=\""+"image.jpg") RequestBody headImage,
                                       @Query("api_key")String  apiKey,
                                       @Query("api_secret")String api_secret,
                                       @Query("return_attributes") String return_attributes
                                       );
}
