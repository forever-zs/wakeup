package com.wakeup.forever.wakeup.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.bean.Share;
import com.wakeup.forever.wakeup.presenter.adapter.CommonShareAdapter;
import com.wakeup.forever.wakeup.presenter.fragmentPresenter.CommonShareFragmentPresenter;
import com.wakeup.forever.wakeup.utils.LogUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
@RequiresPresenter(CommonShareFragmentPresenter.class)
public class CommonShareFragment extends BeamFragment<CommonShareFragmentPresenter> {


    @Bind(R.id.rv_commonShares)
    RecyclerView rvCommonShares;

    private CommonShareAdapter commonShareAdapter;

    public CommonShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_common_share, container, false);
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

    private void initView() {
        LogUtil.e("进入initView");
        getPresenter().initData();
    }

    public void showShareList(ArrayList<Share> shareList){
        LogUtil.e("进入showShareList");
        LogUtil.e(shareList.get(0).getTitle());
        commonShareAdapter=new CommonShareAdapter(shareList,getActivity());
        rvCommonShares.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCommonShares.setAdapter(commonShareAdapter);
    }
}
