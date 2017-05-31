package com.wakeup.forever.wakeup.view.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.bean.CommonShare;
import com.wakeup.forever.wakeup.presenter.adapter.CommonShareAdapter;
import com.wakeup.forever.wakeup.presenter.fragmentPresenter.CommonShareFragmentPresenter;
import com.wakeup.forever.wakeup.utils.SnackBarUtil;
import com.wakeup.forever.wakeup.view.activity.PublishCommonShareActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

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
    @Bind(R.id.srl_refreshCommonShare)
    SwipeRefreshLayout srlRefreshCommonShare;
    @Bind(R.id.fab_addShare)
    FloatingActionButton fabAddShare;
    @Bind(R.id.fl_commonShare)
    FrameLayout flCommonShare;

    private CommonShareAdapter commonShareAdapter;
    private ArrayList<CommonShare> commonShareList;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem;

    public CommonShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_common_share, container, false);
        ButterKnife.bind(this, view);
        getPresenter().refreshData();
        srlRefreshCommonShare.setColorSchemeResources(R.color.mainColor);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        initListener();
    }

    private void initListener() {
        srlRefreshCommonShare.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().refreshData();
            }
        });
        fabAddShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PublishCommonShareActivity.class));
            }
        });

        rvCommonShares.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && (lastVisibleItem+1 == commonShareList.size())) {
                    getPresenter().loadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void initView() {
        commonShareList = new ArrayList<CommonShare>();
        commonShareAdapter = new CommonShareAdapter(this, commonShareList);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvCommonShares.setLayoutManager(linearLayoutManager);
        rvCommonShares.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(getContext())
                .drawable(R.drawable.shape_common_divider)
                .size(10)
                .build()
        );
        rvCommonShares.setAdapter(commonShareAdapter);
        //getPresenter().refreshData();
        getPresenter().initDataFromCache();
    }

    public ArrayList<CommonShare> getCommonShareList() {
        return commonShareList;
    }

    public void refreshData() {
        srlRefreshCommonShare.setRefreshing(false);
        commonShareAdapter.notifyDataSetChanged();
    }

    public void showSnackBar(String text){
        SnackBarUtil.showText(flCommonShare,text);
    }
    public void autoRefresh()
    {
        srlRefreshCommonShare.post(new Runnable() {
            @Override
            public void run() {
                srlRefreshCommonShare.setRefreshing(true);
            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                srlRefreshCommonShare.setRefreshing(false);
                getPresenter().refreshData();
            }
        }, 1000);
    }
}
