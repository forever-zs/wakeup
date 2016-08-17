package com.wakeup.forever.wakeup.app;

import android.app.Application;
import android.content.Context;

import com.jude.beam.Beam;

/**
 * Created by forever on 2016/8/1.
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Beam.init(this);
    }

    public static Context getGlobalContext() {
        return context;
    }
}
