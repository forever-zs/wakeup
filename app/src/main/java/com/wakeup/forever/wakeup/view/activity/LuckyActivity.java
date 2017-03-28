package com.wakeup.forever.wakeup.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.utils.UiUtil;
import com.wakeup.forever.wakeup.widget.LotteryDrawView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LuckyActivity extends AppCompatActivity {

    @Bind(R.id.ld_lucky)
    LotteryDrawView ldLucky;
    @Bind(R.id.btn_start)
    Button btnStart;
    @Bind(R.id.ib_back)
    ImageButton ibBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    private boolean isStopping=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky);
        //UiUtil.immerseStatusBar(this);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ldLucky.isStart()){
                    ldLucky.startRotate(5);
                    btnStart.setText("停止");
                    isStopping=false;
                }
                else{
                    if(ldLucky.isShouldStop()&&(!isStopping)){
                        ldLucky.stopRotate();
                        btnStart.setText("开始");
                        isStopping=true;
                    }
                }
            }
        });
    }

    @OnClick({R.id.ib_back, R.id.tv_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_title:
                break;
            case R.id.rl_title:
                break;
        }
    }
}
