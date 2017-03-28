package com.wakeup.forever.wakeup.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.presenter.adapter.SharePagerAdapter;
import com.wakeup.forever.wakeup.presenter.fragmentPresenter.UpdateFragmentPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */

@RequiresPresenter(UpdateFragmentPresenter.class)
public class UpdateFragment extends BeamFragment<UpdateFragmentPresenter> {


    @Bind(R.id.tl_share)
    TabLayout tlShare;
    @Bind(R.id.vp_share)
    ViewPager vpShare;

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private OfficialShareFragment officialShareFragment;
    private CommonShareFragment commonShareFragment;
    private FragmentManager fragmentManager;
    private SharePagerAdapter sharePagerAdapter;

    public UpdateFragment() {
        // Required empty public constructor
        officialShareFragment=new OfficialShareFragment();
        commonShareFragment =new CommonShareFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    @Override
    public void onStart() {
        super.onStart();

        //commonShareFragment.start();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        fragments=new ArrayList<Fragment>();



        fragments.add(commonShareFragment);
        fragments.add(officialShareFragment);
        fragmentManager=getActivity().getSupportFragmentManager();

        titles=new ArrayList<String>();
        titles.add("动态");
        titles.add("官方通知");


        tlShare.setTabMode(TabLayout.MODE_FIXED);

        tlShare.addTab(tlShare.newTab().setText(titles.get(0)));
        tlShare.addTab(tlShare.newTab().setText(titles.get(1)));

        sharePagerAdapter=new SharePagerAdapter(fragmentManager,fragments,titles);
        vpShare.setAdapter(sharePagerAdapter);
        tlShare.setupWithViewPager(vpShare);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        if(officialShareFragment==null&&childFragment instanceof OfficialShareFragment){
            officialShareFragment= (OfficialShareFragment) childFragment;
        }
        else if(commonShareFragment==null&&childFragment instanceof CommonShareFragment){
            commonShareFragment= (CommonShareFragment) childFragment;
        }
    }
}
