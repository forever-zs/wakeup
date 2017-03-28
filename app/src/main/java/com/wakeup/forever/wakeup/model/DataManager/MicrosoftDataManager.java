package com.wakeup.forever.wakeup.model.DataManager;

import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.bean.Emotion;
import com.wakeup.forever.wakeup.model.bean.FaceAnalyse;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.Weather;
import com.wakeup.forever.wakeup.model.service.FaceService;
import com.wakeup.forever.wakeup.model.service.WeatherService;
import com.wakeup.forever.wakeup.utils.MicresoftRetrofitUtil;
import com.wakeup.forever.wakeup.utils.RetrofitUtil;
import com.wakeup.forever.wakeup.utils.WeatherRetrofitUtil;

import java.util.ArrayList;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by forever on 2016/11/26.
 */

public class MicrosoftDataManager {

    private FaceService faceService;

    private WeatherService weatherService;

    private static MicrosoftDataManager microsoftDataManager;

    private MicrosoftDataManager(){
        faceService = MicresoftRetrofitUtil.getRetrofit().create(FaceService.class);
        weatherService= WeatherRetrofitUtil.getRetrofit().create(WeatherService.class);
    }

    public static MicrosoftDataManager getInstance(){
        if(microsoftDataManager==null){
            microsoftDataManager=new MicrosoftDataManager();
            return microsoftDataManager;
        }
        else{
            return microsoftDataManager;
        }
    }

    public void addEmotion(RequestBody requestBody,Subscriber<FaceAnalyse> subscriber){
        faceService.addEmotion(
                requestBody,
                GlobalConstant.FACE_API_KEY,
                GlobalConstant.FACE_API_SECRET,
                GlobalConstant.FACE_RETURN_ATTRIBUTES
                )
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getWeatherInfo(Subscriber<Weather> subscriber){
        weatherService.getWeatherInfo("苏州")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
