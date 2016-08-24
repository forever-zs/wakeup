package com.wakeup.forever.wakeup.app;

import android.content.Context;

import com.jude.beam.Beam;
import com.wakeup.forever.wakeup.config.GlobalConstant;

import org.litepal.LitePalApplication;

import cn.smssdk.SMSSDK;

/**
 * Created by forever on 2016/8/1.
 */
public class App extends LitePalApplication {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Beam.init(this);
        SMSSDK.initSDK(this, GlobalConstant.SmsKey, GlobalConstant.SmsSecret);
    }

    public static Context getGlobalContext() {
        return context;
    }
}
