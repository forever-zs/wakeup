package com.wakeup.forever.wakeup.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.base.BaseActivity;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.presenter.activityPresenter.MainActivityPresenter;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.fragment.MainFragment;
import com.wakeup.forever.wakeup.view.fragment.UpdateFragment;
import com.wakeup.forever.wakeup.widget.CircleImageView;
import com.wakeup.forever.wakeup.view.fragment.UserCenterFragment;

import java.io.File;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

@RequiresPresenter(MainActivityPresenter.class)
public class MainActivity extends BaseActivity<MainActivityPresenter> {


    @Bind(R.id.rb_main)
    RadioButton rbMain;
    @Bind(R.id.rb_update)
    RadioButton rbUpdate;
    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rg_menuList)
    RadioGroup rgMenuList;
    @Bind(R.id.fl_main)
    FrameLayout flMain;

    private FragmentManager fragmentManager;
    private MainFragment mainFragment;
    private UpdateFragment updateFragment;
    private DrawerLayout mDrawerLayout;
    private long lastPressBackTime;
    private CircleImageView ivHeadImage;
    private UserCenterFragment userCenterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityManager.addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager=getSupportFragmentManager();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        ivHeadImage= (CircleImageView) navView.getHeaderView(0).findViewById(R.id.nav_headerImage);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_homepage);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()){
                    case R.id.nav_homepage:{

                    } break;
                    case R.id.nav_person:{
                        Intent i = new Intent(MainActivity.this, HomeDetailActivity.class);
                        i.putExtra(GlobalConstant.FLAG, HomeDetailActivity.USER_CENTER);
                        startActivity(i);
                    } break;
                    case R.id.nav_shop:{
                        Intent i = new Intent(MainActivity.this,PointShopActivity.class);
                        startActivity(i);
                    } break;
                    case R.id.nav_info:{
                        Intent i = new Intent(MainActivity.this, HomeDetailActivity.class);
                        i.putExtra(GlobalConstant.FLAG, HomeDetailActivity.ABOUT);
                        startActivity(i);
                    } break;
                    case R.id.nav_settings:{
                        Intent i = new Intent(MainActivity.this, HomeDetailActivity.class);
                        i.putExtra(GlobalConstant.FLAG, HomeDetailActivity.SETTING);
                        startActivity(i);
                    }
                }

                return true;
            }
        });
        initView();
        initApp();
    }


    public void initView(){
        getPresenter().initData();

        rbMain.setChecked(true);
        //初始化fragment
        mainFragment=new MainFragment();
        updateFragment=new UpdateFragment();
        userCenterFragment = new UserCenterFragment();

        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.fl_main,mainFragment);
        transaction.add(R.id.fl_main,updateFragment);
        //transaction.add(R.id.fl_main,homeFragment);
        transaction.add(R.id.fl_main,userCenterFragment);
        hideAll(transaction);
        transaction.show(mainFragment);
        transaction.commit();

        rgMenuList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                hideAll(transaction);
                if(checkedId==rbMain.getId()){
                    transaction.show(mainFragment);
                }
                else if(checkedId==rbHome.getId()){
                    transaction.show(userCenterFragment);
                }
                else{
                    transaction.show(updateFragment);
                }
                transaction.commit();
            }
        });}


    public void hideAll(FragmentTransaction transaction){
        transaction.hide(mainFragment);
        transaction.hide(updateFragment);
        transaction.hide(userCenterFragment);
    }

    @Override
    public void onBackPressed() {
        long pressTime = new Date().getTime() / 1000;
        if (pressTime - lastPressBackTime < 2) {
            super.onBackPressed();
        } else {
            lastPressBackTime = pressTime;
            ToastUtil.showText("再点一次退出");
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if(mainFragment==null&&fragment instanceof MainFragment){
            mainFragment= (MainFragment) fragment;
        }
        else if(updateFragment==null&&fragment instanceof UpdateFragment){
            updateFragment= (UpdateFragment) fragment;
        }
        else if(userCenterFragment==null&&fragment instanceof UserCenterFragment){
            userCenterFragment = (UserCenterFragment) fragment;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
       // super.onSaveInstanceState(outState);
    }


    private void initApp() {
        File file=new File(Environment.getExternalStorageDirectory()+"/wakeup");
        if(!file.exists()){
            file.mkdir();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    public void showUserInfo(User user){
        LogUtil.e("mainActivity："+user);
        Glide.with(getBaseContext())
                .load(user.getHeadURL())
                .error(R.drawable.head)
                .crossFade()
                .into(ivHeadImage);
    }
}
