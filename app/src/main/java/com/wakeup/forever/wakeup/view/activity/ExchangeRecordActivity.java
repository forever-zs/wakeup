package com.wakeup.forever.wakeup.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.base.BaseActivity;
import com.wakeup.forever.wakeup.presenter.activityPresenter.ExchangeRecordActivityPresenter;

@RequiresPresenter(ExchangeRecordActivityPresenter.class)
public class ExchangeRecordActivity extends BaseActivity<ExchangeRecordActivityPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_record);
    }
}
