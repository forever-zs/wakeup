package com.wakeup.forever.wakeup.model.DataManager;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by forever on 2016/8/23.
 */
public class ActivityManager {
    private static ArrayList<Activity> activities=new ArrayList<Activity>();

    public static void finishAll(){
        for(Activity activity:activities){
            activity.finish();
        }
    }

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
}
