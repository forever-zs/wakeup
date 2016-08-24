package com.wakeup.forever.wakeup.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

/**
 * Created by forever on 2016/8/23.
 */
public class UiUtil {

    public static void immerseStatusBar(Activity aty) {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = aty.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            aty.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
