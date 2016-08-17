package com.wakeup.forever.wakeup.utils;

import android.widget.Toast;

import com.wakeup.forever.wakeup.app.App;

/**
 * Created by forever on 2016/8/1.
 * 单例Toast，防止用户连续点击产生连续产生多个Toast
 */
public class ToastUtil {
    private static Toast toast;

    public static void showText(String content){
        if(toast==null){
            toast=Toast.makeText(App.getGlobalContext(),content,Toast.LENGTH_SHORT);
        }
        else{
            toast.setText(content);
        }
        toast.show();
    }
}
