package com.wakeup.forever.wakeup.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.bean.Share;
import com.wakeup.forever.wakeup.presenter.adapter.OfficialShareAdapter;
import com.wakeup.forever.wakeup.presenter.fragmentPresenter.OfficialShareFragmentPresenter;
import com.wakeup.forever.wakeup.utils.SnackBarUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
@RequiresPresenter(OfficialShareFragmentPresenter.class)
public class OfficialShareFragment extends BeamFragment<OfficialShareFragmentPresenter> {


    @Bind(R.id.rv_officialShare)
    RecyclerView rvOfficialShare;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    @Bind(R.id.ll_officialShare)
    FrameLayout llOfficialShare;

    private OfficialShareAdapter officialShareAdapter;
    private ArrayList<Share> shareList;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem;  //用於標記最後一個可見的item
    private HeaderAndFooterWrapper wrapper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_official_share, container, false);
        ButterKnife.bind(this, view);
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
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().refreshData();
            }
        });
        rvOfficialShare.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && (lastVisibleItem + 2 > shareList.size())) {
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initView() {
        shareList = new ArrayList<Share>();
        officialShareAdapter = new OfficialShareAdapter(shareList, getActivity());

        //通過裝飾器為recycleView設置header
        wrapper = new HeaderAndFooterWrapper(officialShareAdapter);
        wrapper.addHeaderView(LayoutInflater.from(getContext()).inflate(R.layout.layout_official_share_header, null));
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvOfficialShare.setLayoutManager(linearLayoutManager);   //設置佈局管理器
        rvOfficialShare.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(getContext())
                .drawable(R.drawable.shape_official_divider)
                .size(2)
                .build()
        );
        rvOfficialShare.setAdapter(wrapper);//將裝飾器設置給recycleView
        wrapper.notifyDataSetChanged();    //通知數據的變化
        //初始化數據
        getPresenter().initData();
        srlRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
    }

    public void showShareList() {
        wrapper.notifyDataSetChanged();
    }


    public ArrayList<Share> getShareList() {
        return shareList;
    }

    public void stopRefresh() {
        srlRefresh.setRefreshing(false);
    }

    public void showSnackBar(String text) {
        SnackBarUtil.showText(llOfficialShare, text);
    }
}
