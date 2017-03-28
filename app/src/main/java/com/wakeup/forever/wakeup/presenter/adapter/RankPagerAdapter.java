package com.wakeup.forever.wakeup.presenter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wakeup.forever.wakeup.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by forever on 2016/9/10.
 */
public class RankPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> fragments;

    private ArrayList<String> titles;

    public RankPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
        LogUtil.e(fragments.size()+"大小");
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position%titles.size());
    }
}
