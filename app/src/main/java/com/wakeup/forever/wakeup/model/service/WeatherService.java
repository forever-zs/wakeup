package com.wakeup.forever.wakeup.model.service;

import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.SplashImage;
import com.wakeup.forever.wakeup.model.bean.Weather;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by forever on 2016/12/4.
 */

public interface WeatherService {
    @GET("weather_mini")
    Observable<Weather> getWeatherInfo(@Query("city")String city);
}
