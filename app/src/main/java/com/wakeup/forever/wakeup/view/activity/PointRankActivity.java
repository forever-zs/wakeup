package com.wakeup.forever.wakeup.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.presenter.activityPresenter.PointRankActivityPresenter;
import com.wakeup.forever.wakeup.presenter.adapter.RankPagerAdapter;
import com.wakeup.forever.wakeup.view.fragment.AllPointRankFragment;
import com.wakeup.forever.wakeup.view.fragment.MonthPointRankFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

@RequiresPresenter(PointRankActivityPresenter.class)
public class PointRankActivity extends FragmentActivity {

    @Bind(R.id.tl_pointRank)
    TabLayout tlPointRank;
    @Bind(R.id.vp_pointRank)
    ViewPager vpPointRank;

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private AllPointRankFragment allPointRankFragment;
    private MonthPointRankFragment monthPointRankFragment;

    private FragmentManager fragmentManager;
    private RankPagerAdapter rankPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_rank);
        ButterKnife.bind(this);
        allPointRankFragment=new AllPointRankFragment();
        monthPointRankFragment=new MonthPointRankFragment();
        initData();
        initView();
    }

    private void initView() {

    }

    private void initData() {
        fragments=new ArrayList<Fragment>();
        titles=new ArrayList<String>();
        fragmentManager=getSupportFragmentManager();

        fragments.add(monthPointRankFragment);
        fragments.add(allPointRankFragment);
        titles.add("晨起月榜");
        titles.add("晨起总榜");
        tlPointRank.setTabMode(TabLayout.MODE_FIXED);
        tlPointRank.addTab(tlPointRank.newTab().setText(titles.get(0)));
        tlPointRank.addTab(tlPointRank.newTab().setText(titles.get(1)));
        rankPagerAdapter=new RankPagerAdapter(fragmentManager,fragments,titles);
        vpPointRank.setAdapter(rankPagerAdapter);
        tlPointRank.setupWithViewPager(vpPointRank);
    }
}
