package com.wakeup.forever.wakeup.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.jude.beam.expansion.BeamBaseActivity;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.view.fragment.UserCenterFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeDetailActivity extends BeamBaseActivity {

    @Bind(R.id.fl_homeDetail)
    FrameLayout flHomeDetail;


    private FragmentManager fragmentManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
        setContentView(R.layout.activity_home_detail);
        ButterKnife.bind(this);
        initView();
        int flag=getIntent().getIntExtra(GlobalConstant.FLAG,GlobalConstant.USER_CENTER);
        changeFragment(flag);
    }

    private void initView() {
        fragmentManager=getSupportFragmentManager();
    }

    public void changeFragment(int flag){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        if(flag==GlobalConstant.USER_CENTER){
            fragmentTransaction.replace(R.id.fl_homeDetail,new UserCenterFragment());
        }
        fragmentTransaction.commit();
    }


}
