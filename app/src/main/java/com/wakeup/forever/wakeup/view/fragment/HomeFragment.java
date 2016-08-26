package com.wakeup.forever.wakeup.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.presenter.fragmentPresenter.HomeFragmentPresenter;
import com.wakeup.forever.wakeup.utils.SnackBarUtil;
import com.wakeup.forever.wakeup.view.activity.HomeDetailActivity;
import com.wakeup.forever.wakeup.view.activity.LoginActivity;
import com.wakeup.forever.wakeup.widget.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
@RequiresPresenter(HomeFragmentPresenter.class)
public class HomeFragment extends BeamFragment<HomeFragmentPresenter> {

    /*
           绑定的view
           @forever
     */
    @Bind(R.id.civ_head)
    CircleImageView civHead;
    @Bind(R.id.tv_grade)
    TextView tvGrade;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.rl_userCenter)
    RelativeLayout rlUserCenter;
    @Bind(R.id.rl_signIn)
    RelativeLayout rlSignIn;
    @Bind(R.id.rl_plan)
    RelativeLayout rlPlan;
    @Bind(R.id.rl_footMark)
    RelativeLayout rlFootMark;
    @Bind(R.id.rl_aboutUs)
    RelativeLayout rlAboutUs;
    @Bind(R.id.rl_setting)
    RelativeLayout rlSetting;
    @Bind(R.id.ll_home)
    LinearLayout llHome;

    private boolean updateInfo = true;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateInfo = true;
        initView();
        getPresenter().initData();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {
        civHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        rlUserCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), HomeDetailActivity.class);
                i.putExtra(GlobalConstant.FLAG, GlobalConstant.USER_CENTER);
                startActivity(i);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void showUserInfo(User user) {
        if (user.getHeadURL() != null) {
            Glide.with(getActivity())
                    .load(user.getHeadURL())
                    .error(R.drawable.head)
                    .crossFade()
                    .into(civHead);
        }
        if (user.getName() != null) {
            tvName.setText(user.getName());
        }
    }

    public void showSnackBar(String text) {
        SnackBarUtil.showText(llHome, text);
    }

    public void setHeadClickable(boolean flag) {
        if (civHead != null) {
            civHead.setClickable(flag);
        }

    }

}
