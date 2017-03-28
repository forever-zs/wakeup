package com.wakeup.forever.wakeup.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.base.BaseActivity;
import com.wakeup.forever.wakeup.model.bean.Banner;
import com.wakeup.forever.wakeup.model.bean.ShopItem;
import com.wakeup.forever.wakeup.presenter.activityPresenter.PointShopActivityPresenter;
import com.wakeup.forever.wakeup.presenter.adapter.BannerAdapter;
import com.wakeup.forever.wakeup.presenter.adapter.ShopItemAdapter;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RequiresPresenter(PointShopActivityPresenter.class)
public class PointShopActivity extends BaseActivity<PointShopActivityPresenter> {

    ViewPager vpBanner;
    LinearLayout llCircle;
    @Bind(R.id.rv_shopItem)
    RecyclerView rvShopItem;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;
    @Bind(R.id.iv_scan)
    ImageView ivScan;


    private BannerAdapter bannerAdapter;   //轮播图的适配器
    private ArrayList<Banner> bannerList;  //轮播图展示的数据集
    private int pointIndex = 0;             //轮播小圆点的下标
    private boolean isContinue = false;   //轮播是否自动继续


    private ShopItemAdapter shopItemAdapter;
    private LinkedList<ShopItem> shopItemList;
    private HeaderAndFooterWrapper wrapper;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_shop);
        ButterKnife.bind(this);
        initData();
        initAction();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what != -1) {
                vpBanner.setCurrentItem(vpBanner.getCurrentItem() + 1);
            }                                                   //接受轮播图进程发的消息用来改变轮播图的图片
        }
    };


    private void initAction() {
        int imageIndex = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2 % bannerList.size());
        vpBanner.setCurrentItem(imageIndex);
        llCircle.getChildAt(pointIndex).setEnabled(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isContinue) {
                    try {
                        Thread.sleep(2000);
                        handler.sendEmptyMessage(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getAllShops();
            }
        });
    }

    private void initData() {
        initBannerData();
        initShopItemData();

    }

    private void initShopItemData() {
        shopItemList = new LinkedList<>();
        getPresenter().getAllShops();
        shopItemAdapter = new ShopItemAdapter(shopItemList, this);
        wrapper = new HeaderAndFooterWrapper(shopItemAdapter);
        wrapper.addHeaderView(headerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getBaseContext(), 2);
        rvShopItem.setLayoutManager(gridLayoutManager);
        rvShopItem.setAdapter(wrapper);

    }

    private void initBannerData() {
        headerView = LayoutInflater.from(getBaseContext()).inflate(R.layout.layout_shop_item_header, null);
        vpBanner = (ViewPager) headerView.findViewById(R.id.vp_banner);
        llCircle = (LinearLayout) headerView.findViewById(R.id.ll_circle);
        bannerList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Banner banner = new Banner();
            banner.setImageUrl("http://s3.lvjs.com.cn/uploads/pc/place2/16137/1373355656434.jpg");
            bannerList.add(banner);
        }


        for (int i = 0; i < bannerList.size(); i++) {
            View view = new View(getBaseContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.leftMargin = 10;
            view.setBackgroundResource(R.drawable.banner_circle_seletor);
            view.setLayoutParams(params);
            view.setEnabled(false);
            llCircle.addView(view);
        }

        bannerAdapter = new BannerAdapter(bannerList, this.getBaseContext());
        vpBanner.setAdapter(bannerAdapter);
        vpBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int newPointIndex = position % bannerList.size();
                llCircle.getChildAt(newPointIndex).setEnabled(true);
                llCircle.getChildAt(pointIndex).setEnabled(false);
                pointIndex = newPointIndex;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    protected void onDestroy() {
        isContinue = false;
        super.onDestroy();
    }

    public void onGetAllShopSuccess(LinkedList<ShopItem> shopList) {
        shopItemList.clear();
        shopItemList.addAll(shopList);
        wrapper.notifyDataSetChanged();
    }

    public void onGetAllShopFail(String message) {
        ToastUtil.showText(message);
    }

    public void stopRefresh() {
        srlRefresh.setRefreshing(false);
    }

    @OnClick(R.id.iv_scan)
    public void onClick() {
       if(checkPermission()) {
           Intent intent = new Intent(getBaseContext(), CaptureActivity.class);
           startActivity(intent);
       }
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {// 没有权限。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ToastUtil.showText("未获得相机权限");
            } else {
                // 申请授权。

                AndPermission.with(this)
                        .requestCode(102)
                        .permission(Manifest.permission.CAMERA)
                        .send();
            }
            return false;
        } else {
            return true;
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
// 只需要调用这一句，剩下的AndPermission自动完成。
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults, listener);
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode) {
            if (requestCode == 100) {
                LogUtil.e("申请成功");
            }
        }

        @Override
        public void onFailed(int requestCode) {
            if (requestCode == 100) {
                LogUtil.e("申请失败");
            }
        }
    };

}
