package com.wakeup.forever.wakeup.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.presenter.adapter.UserGuideAdapter;
import com.wakeup.forever.wakeup.utils.UiUtil;
import com.wakeup.forever.wakeup.widget.LotteryDrawView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GuideActivity extends AppCompatActivity {

    @Bind(R.id.ll_guideCircle)
    LinearLayout llGuideCircle;
    @Bind(R.id.vp_userGuide)
    ViewPager vpUserGuide;
    @Bind(R.id.btn_start)
    Button btnStart;
    @Bind(R.id.activity_guide)
    RelativeLayout activityGuide;

    private int resourse[] = {R.drawable.guide1, R.drawable.guide2, R.drawable.guide3};
    private ArrayList<Bitmap> guideImageList = new ArrayList<>();
    private int pointIndex = 0;
    private UserGuideAdapter userGuideAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityManager.addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        UiUtil.immerseStatusBar(this);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    private void initView() {
        btnStart.setVisibility(View.INVISIBLE);
        for (int i = 0; i < resourse.length; i++) {
            View view = new View(getBaseContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.leftMargin = 10;
            view.setBackgroundResource(R.drawable.banner_circle_seletor);
            view.setLayoutParams(params);
            view.setEnabled(false);
            llGuideCircle.addView(view);
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });


    }

    private void initData() {
        for (int i = 0; i < resourse.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourse[i]);
            guideImageList.add(bitmap);
        }
        userGuideAdapter = new UserGuideAdapter(guideImageList, getBaseContext());
        llGuideCircle.getChildAt(pointIndex).setEnabled(true);
        vpUserGuide.setAdapter(userGuideAdapter);
        vpUserGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int newPointIndex = position;
                llGuideCircle.getChildAt(newPointIndex).setEnabled(true);
                llGuideCircle.getChildAt(pointIndex).setEnabled(false);
                pointIndex = newPointIndex;
                if (pointIndex == (resourse.length - 1)) {
                    btnStart.setVisibility(View.VISIBLE);
                } else {
                    btnStart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
