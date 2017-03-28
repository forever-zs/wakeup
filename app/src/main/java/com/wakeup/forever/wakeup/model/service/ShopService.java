package com.wakeup.forever.wakeup.model.service;

import com.wakeup.forever.wakeup.model.bean.ExchangeRecord;
import com.wakeup.forever.wakeup.model.bean.ExchangeRecordForUser;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.ShopItem;
import com.wakeup.forever.wakeup.model.bean.TodayRank;

import java.util.ArrayList;
import java.util.LinkedList;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by forever on 2016/11/25.
 */

public interface ShopService {
    @GET("shop/getAllShops.do")
    Observable<HttpResult<LinkedList<ShopItem>>> getAllShops();

    @POST("shop/exchangeShop.do")
    Observable<HttpResult<ExchangeRecord>> exchangeShop(@Query("token") String token,@Query("shopId")Integer shopId);

    @GET("shop/getExchangeRecord.do")
    Observable<HttpResult<LinkedList<ExchangeRecordForUser>>> getExchangeRecord(@Query("token") String token);

    @GET("shop/resumeExchangeRecord.do")
    Observable<HttpResult<ShopItem>> resumeExchangeRecord(@Query("shopId") int shopId,@Query("userPhone") String userPhone,@Query("code") String code);
}
