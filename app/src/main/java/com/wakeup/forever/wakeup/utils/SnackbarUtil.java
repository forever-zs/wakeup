package com.wakeup.forever.wakeup.utils;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;

import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.app.App;

/**
 * Created by forever on 2016/8/24.
 */
public class SnackBarUtil {
    public static  void showText(ViewGroup container,String text){
        Snackbar.make(container,text,Snackbar.LENGTH_SHORT)
                .setAction("好的", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setActionTextColor(App.getGlobalContext().getResources().getColor(R.color.mainColor))
                .show();
    }
}
