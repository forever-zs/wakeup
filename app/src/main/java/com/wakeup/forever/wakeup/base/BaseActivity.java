package com.wakeup.forever.wakeup.base;

import android.os.Bundle;

import com.jude.beam.bijection.Presenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

/**
 * Created by forever on 2016/12/6.
 */

public class BaseActivity<T extends Presenter> extends BeamBaseActivity<T> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        PushAgent.getInstance(this).onAppStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getPackageName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MobclickAgent.onPageEnd(getPackageName());
        MobclickAgent.onPause(this);
    }
}
