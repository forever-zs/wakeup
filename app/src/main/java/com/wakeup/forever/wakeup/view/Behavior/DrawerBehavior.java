package com.wakeup.forever.wakeup.view.Behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.wakeup.forever.wakeup.utils.LogUtil;

/**
 * Created by forever on 2016/12/8.
 */

public class DrawerBehavior extends CoordinatorLayout.Behavior<View> {

    private int mFrameMaxHeight = 100;
    private int mStartY;

    public DrawerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if(mStartY == 0) {
            mStartY = (int) dependency.getY();
        }

//计算toolbar从开始移动到最后的百分比
        float percent = dependency.getY()/mStartY;

        LogUtil.e(percent+"哈哈");

//改变child的坐标(从消失，到可见)
        child.setY(child.getHeight()*(1-percent) - child.getHeight());
        return true;
    }
}
