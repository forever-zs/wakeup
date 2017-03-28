package com.wakeup.forever.wakeup.view.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.presenter.fragmentPresenter.UserCenterFragmentPresenter;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.SnackBarUtil;
import com.wakeup.forever.wakeup.widget.CircleImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */

@RequiresPresenter(UserCenterFragmentPresenter.class)
public class UserCenterFragment extends BeamFragment<UserCenterFragmentPresenter> {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.rl_headImage)
    RelativeLayout rlHeadImage;
    @Bind(R.id.rl_name)
    RelativeLayout rlName;
    @Bind(R.id.rl_birth)
    RelativeLayout rlBirth;
    @Bind(R.id.rl_sex)
    RelativeLayout rlSex;
    @Bind(R.id.rl_campus)
    RelativeLayout rlCampus;
    @Bind(R.id.rl_signature)
    RelativeLayout rlSignature;
    @Bind(R.id.cim_head)
    CircleImageView cimHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_birth)
    TextView tvBirth;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_campus)
    TextView tvCampus;
    @Bind(R.id.tv_signature)
    TextView tvSignature;
    @Bind(R.id.ll_userCenter)
    LinearLayout llUserCenter;

    UserCenterFragmentPresenter userCenterFragmentPresenter;
    private ProgressDialog progressDialog;
    private EditText inputUserMessage;
    private DatePicker datePicker;
    //实时获取datePicker的值
    private Calendar calendar=Calendar.getInstance();
    private SimpleDateFormat myFmt=new SimpleDateFormat("yyyy年MM月dd日");


    public UserCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_center, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        dismissProgressDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        userCenterFragmentPresenter.initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userCenterFragmentPresenter = getPresenter();

    }

    private void initView() {

        rlHeadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCenterFragmentPresenter.getImageUtil().showDialog();
            }
        });

        rlName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=getAlterDialogBuilder()
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (inputUserMessage.getText().toString().isEmpty()) {
                                    showSnackBar(GlobalConstant.INPUT_EMPTY_MESSAGE);
                                } else {
                                    tvName.setText(inputUserMessage.getText().toString());
                                    User user = new User();
                                    user.setName(inputUserMessage.getText().toString());
                                    userCenterFragmentPresenter.updateUserInfo(user);
                                }
                            }
                        })
                        .create();
                dialog.show();
                inputUserMessage= (EditText) dialog.findViewById(R.id.input_userMessage);
            }
        });

        rlCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=getAlterDialogBuilder()
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (inputUserMessage.getText().toString().isEmpty()) {
                                    showSnackBar(GlobalConstant.INPUT_EMPTY_MESSAGE);
                                } else {
                                    tvCampus.setText(inputUserMessage.getText().toString());
                                    User user = new User();
                                    user.setCampus(inputUserMessage.getText().toString());
                                    userCenterFragmentPresenter.updateUserInfo(user);
                                }
                            }
                        })
                        .create();
                dialog.show();
                inputUserMessage= (EditText) dialog.findViewById(R.id.input_userMessage);
            }
        });

        rlSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=getAlterDialogBuilder()
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String message=inputUserMessage.getText().toString();
                                if (message.isEmpty()) {
                                    showSnackBar(GlobalConstant.INPUT_EMPTY_MESSAGE);
                                }
                                else if(!(message.equals("男")||message.equals("女"))){
                                    showSnackBar("请输入'男'或'女'");
                                }
                                else {
                                    tvSex.setText(inputUserMessage.getText().toString());
                                    User user = new User();
                                    user.setSex(inputUserMessage.getText().toString());
                                    userCenterFragmentPresenter.updateUserInfo(user);
                                }
                            }
                        })
                        .create();
                dialog.show();
                inputUserMessage= (EditText) dialog.findViewById(R.id.input_userMessage);
            }
        });

        rlSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=getAlterDialogBuilder()
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String message=inputUserMessage.getText().toString();
                                if (message.isEmpty()) {
                                    showSnackBar(GlobalConstant.INPUT_EMPTY_MESSAGE);
                                }
                                else if(message.length()>20){
                                    showSnackBar("你的签名太长了，亲");
                                }
                                else {
                                    tvSignature.setText(inputUserMessage.getText().toString());
                                    User user = new User();
                                    user.setSignature(inputUserMessage.getText().toString());
                                    userCenterFragmentPresenter.updateUserInfo(user);
                                }
                            }
                        })
                        .create();
                dialog.show();
                inputUserMessage= (EditText) dialog.findViewById(R.id.input_userMessage);
            }
        });

        rlBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=new AlertDialog.Builder(getContext())
                        .setView(R.layout.date_picker_layout)
                        .setCancelable(false)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               if((calendar.getTimeInMillis()>System.currentTimeMillis())||(calendar.get(Calendar.YEAR)<1950)){
                                   showSnackBar("亲，请认真填写呀");
                               }
                                else{
                                   tvBirth.setText(myFmt.format(calendar.getTime()));
                                   User user=new User();
                                   user.setBirth(calendar.getTimeInMillis());
                                   userCenterFragmentPresenter.updateUserInfo(user);
                               }
                            }
                        })
                        .create();
                dialog.show();
                datePicker= (DatePicker) dialog.findViewById(R.id.dp_date);
                datePicker.init(1995, 11, 14, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year,monthOfYear,dayOfMonth);
                    }
                });
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    public void showUserInfo(User user) {
        if (user.getName() != null) {
            tvName.setText(user.getName());
        }
        if (user.getSex() != null) {
            tvSex.setText(user.getSex());
        }
        if (user.getBirth() != null) {
            tvBirth.setText(myFmt.format(new Date(user.getBirth())));
        }
        if (user.getSignature() != null) {
            tvSignature.setText(user.getSignature());
        }
        if (user.getCampus() != null) {
            tvCampus.setText(user.getCampus());
        }
        if (user.getHeadURL() != null) {
            Glide.with(getActivity())
                    .load(user.getHeadURL())
                    .error(R.drawable.head)
                    .crossFade()
                    .into(cimHead);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("zs", "返回结果");
        userCenterFragmentPresenter.disposeResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public CircleImageView getCimHead() {
        return cimHead;
    }

    public void showSnackBar(String text) {
        SnackBarUtil.showText(llUserCenter, text);
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("上传中...");
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

    }

    public AlertDialog.Builder getAlterDialogBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(R.layout.edit_text_layout)
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(R.drawable.logo);
        return builder;
    }

    public void showImageFromFile(File file){
        if(file.exists()){
            LogUtil.e(file.getName());
            Glide.with(getActivity())
                    .load("file://"+file.getAbsolutePath())
                    .crossFade()
                    .error(R.drawable.head)
                    .into(cimHead);
        }
    }

    public void showHeadImageFromBitmap(Bitmap bitmap){
        cimHead.setImageBitmap(bitmap);
    }
}
