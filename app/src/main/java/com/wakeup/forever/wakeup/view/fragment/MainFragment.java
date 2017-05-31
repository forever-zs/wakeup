package com.wakeup.forever.wakeup.view.fragment;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.http.download.DownloadUtils;
import com.wakeup.forever.wakeup.model.bean.UserPoint;
import com.wakeup.forever.wakeup.model.bean.VersionUpdate;
import com.wakeup.forever.wakeup.model.bean.Weather;
import com.wakeup.forever.wakeup.presenter.fragmentPresenter.MainFragmentPresenter;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.utils.UiUtil;
import com.wakeup.forever.wakeup.view.activity.CalendarActivity;
import com.wakeup.forever.wakeup.view.activity.GoodMorningActivity;
import com.wakeup.forever.wakeup.view.activity.LuckyActivity;
import com.wakeup.forever.wakeup.view.activity.MainActivity;
import com.wakeup.forever.wakeup.view.activity.PointRankActivity;
import com.wakeup.forever.wakeup.view.activity.PointShopActivity;
import com.wakeup.forever.wakeup.widget.CircleImageView;
import com.wakeup.forever.wakeup.widget.ColorArcProgressBar;
import com.wakeup.forever.wakeup.widget.WaveView3;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.io.File;
import java.util.Date;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;


@RequiresPresenter(MainFragmentPresenter.class)
public class MainFragment extends BeamFragment<MainFragmentPresenter> {


    @Bind(R.id.btn_sign)
    Button btnSign;
    @Bind(R.id.cpb_signTime)
    ColorArcProgressBar cpbSignTime;
    @Bind(R.id.tv_userName)
    TextView tvUserName;
    @Bind(R.id.civ_head)
    CircleImageView civHead;
    @Bind(R.id.tv_point)
    TextView tvPoint;
    @Bind(R.id.tv_signRecord)
    TextView tvSignRecord;
    @Bind(R.id.rl_signRecord)
    RelativeLayout rlSignRecord;
    @Bind(R.id.tv_lucky)
    TextView tvLucky;
    @Bind(R.id.rl_lucky)
    RelativeLayout rlLucky;
    @Bind(R.id.tv_rank)
    TextView tvRank;
    @Bind(R.id.rl_rank)
    RelativeLayout rlRank;
    @Bind(R.id.tv_shop)
    TextView tvShop;
    @Bind(R.id.rl_shop)
    RelativeLayout rlShop;
    @Bind(R.id.tv_morningMessage)
    TextView tvMorningMessage;
    @Bind(R.id.rl_morningMessage)
    RelativeLayout rlMorningMessage;
    @Bind(R.id.tv_robot)
    TextView tvRobot;
    @Bind(R.id.rl_robot)
    RelativeLayout rlRobot;
    @Bind(R.id.tv_weather)
    TextView tvWeather;
    @Bind(R.id.tv_temperature)
    TextView tvTemperature;
    @Bind(R.id.wv_wave3)
    WaveView3 wvWave3;
    @Bind(R.id.rl_userInfo)
    RelativeLayout rlUserInfo;


    private EditText inputMessage;
    private TextView mathMessage;
    private TextView tvCount;
    //签到选择布局
    private LinearLayout llShake;
    private LinearLayout llCalculate;
    private LinearLayout llSmile;
    private int lastProgress = 0;
    private int shakeCount;
    private TextView tvTodayRank;
    private Button btnStartCamera;
    private TextView tvCameraImage;
    private ImageView ivPhoto;

    private SensorManager sensorManager;
    private Vibrator vibrator;

    private File tempFile;
    private static final int PHOTO_CARMERA = 1;

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;

    }

    private void initData() {
        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
        getPresenter().checkUpdate();
        getPresenter().initUserInfo();
        getPresenter().getWeatherInfo();

        rlMorningMessage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GoodMorningActivity.class);
                startActivity(intent);
            }
        });

        rlRobot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        rlSignRecord.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        rlLucky.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LuckyActivity.class);
                startActivity(intent);
            }
        });

        rlRank.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PointRankActivity.class);
                startActivity(intent);
            }
        });

        rlShop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PointShopActivity.class);
                startActivity(intent);
            }
        });

        btnSign.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalculateDialog();
            }
        });

        wvWave3.setOnWaveAnimationListener(new WaveView3.OnWaveAnimationListener() {
            @Override
            public void OnWaveAnimation(float y) {
                RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) rlUserInfo.getLayoutParams();
                params.setMargins(0,(int)y+2,0,0);
                rlUserInfo.setLayoutParams(params);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public void showUpdateDialog(final VersionUpdate versionUpdate) {
        TextView tvTitle = new TextView(getContext());
        TextView tvLogDetail = new TextView(getContext());
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(R.layout.dialog_version_update)
                .setCancelable(false)
                .setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startDownload(versionUpdate);
                    }
                })
                .create();
        dialog.show();
        tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        tvLogDetail = (TextView) dialog.findViewById(R.id.tv_logDetail);
        tvTitle.setText("发现新版本:" + versionUpdate.getVersionName());
        tvLogDetail.setText(versionUpdate.getDescription());
    }

    private void startDownload(final VersionUpdate versionUpdate) {
        final NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        final Notification.Builder builder = new Notification.Builder(getContext());
        builder.setSmallIcon(R.drawable.head)
                .setTicker("showProgressBar")
                .setOngoing(true).setContentTitle("wakeup更新")
                .setContentText("正在下载");
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wakeup";
        final File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        final boolean flag = true;
        DownloadUtils.getInstance().download(versionUpdate.getUrl(), dir.getAbsolutePath(), "wakeup.apk", new DownloadUtils.RequestCallBack() {
            @Override
            public void onProgress(long progress, long total, boolean done) {

                int i = (int) (((float) progress / (float) total) * 100);
                builder.setProgress(100, i, false);
                if (i % 5 == 0 && lastProgress != i) {
                    lastProgress = i;
                    builder.setContentText(i + "%");
                    notificationManager.notify(0, builder.build());
                }
                if (progress == total) {
                    builder.setContentTitle("wakeup")
                            .setContentText("下载完成")
                            .setProgress(0, 0, false).setOngoing(false);
                    notificationManager.notify(0, builder.build());
                    File file = new File(dir, "wakeup.apk");
                    startInstall(file);
                }
            }

            @Override
            public void onFailure(Call call, Exception e) {

            }
        });

        notificationManager.notify(0, builder.build());
    }

    public void startInstall(File mFile) {
        Context context=this.getActivity();
        /*Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setDataAndType(Uri.fromFile(mFile), "application/vnd.android.package-archive");

        startActivity(install);
        android.os.Process.killProcess(android.os.Process.myPid());*/
        Intent install = new Intent();
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setAction(Intent.ACTION_VIEW);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N)
        {
            Uri uriForFile = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", mFile);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.setDataAndType(uriForFile, context.getContentResolver().getType(uriForFile));
        }
        else
        {
            install.setDataAndType(Uri.fromFile(mFile), "application/vnd.android.package-archive");
        }
        startActivity(install);
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] values = sensorEvent.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            int medumValue = 19;    //摇晃有效值

            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                shakeCount--;
                if (shakeCount > 0) {
                    if(tvCount!=null){
                        tvCount.setText(shakeCount + "次");
                    }
                } else if (shakeCount == 0) {
                    getPresenter().signIn();
                }
            }

            if (shakeCount <= 0) {
                vibrator.vibrate(200);
            }


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    public void showShakeDialog() {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(R.layout.dialog_sign_shanke)
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        shakeCount = 50;
                    }
                })
                .create();
        dialog.show();
        tvCount = (TextView) dialog.findViewById(R.id.tv_count);
    }

    public void showCalculateDialog() {
        Random random = new Random();
        final int x = random.nextInt(10);
        final int y = random.nextInt(10);
        final int z = random.nextInt(100);
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(R.layout.dialog_calculate)
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
                        try {
                            int result = Integer.parseInt(inputMessage.getText().toString());
                            if (x * y == result && z % 2 == 1) {
                                dialog.dismiss();
                                getPresenter().signIn();
                            }
                            if ((x * y + 1) == result && z % 2 == 0) {
                                dialog.dismiss();
                                getPresenter().signIn();
                            }
                            else {
                                ToastUtil.showText("看来你还没睡醒呢╰（‵□′）╯");
                            }
                        } catch (Exception e) {
                            ToastUtil.showText("请认真填写哦，亲");
                        }

                    }
                }).create();
        dialog.show();
        mathMessage = (TextView) dialog.findViewById(R.id.tv_math);
        inputMessage = (EditText) dialog.findViewById(R.id.input_userMessage);
        if (z % 2 == 1)
        {mathMessage.setText(x + "×" + y + "+Sin("+z+"π)"+"=");}
        else
        {
            mathMessage.setText(x + "×" + y + "+Cos("+z+"π)"+"=");
        }
    }

    public void showSignChoiceDialog() {

        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(R.layout.dialog_sign_choice)
                .setCancelable(false)
                .setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
        llShake = (LinearLayout) dialog.findViewById(R.id.ll_shake);
        llCalculate = (LinearLayout) dialog.findViewById(R.id.ll_calculate);
        llSmile = (LinearLayout) dialog.findViewById(R.id.ll_smile);

        llShake.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showShakeDialog();
                dialog.dismiss();
            }
        });

        llCalculate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalculateDialog();
                dialog.dismiss();
            }
        });

        llSmile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showCameraSignDialog();
                dialog.dismiss();
            }
        });

    }

    private void showCameraSignDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(R.layout.dialog_camera_sign)
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("上传", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!tvCameraImage.getText().toString().equals("未选择图片")) {
                            getPresenter().camera(tempFile);
                            dialog.dismiss();
                        } else {
                            ToastUtil.showText("未选择图片");
                        }

                    }
                })
                .create();
        dialog.show();
        btnStartCamera = (Button) dialog.findViewById(R.id.btn_start);
        tvCameraImage = (TextView) dialog.findViewById(R.id.tv_cameraImage);
        ivPhoto = (ImageView) dialog.findViewById(R.id.iv_photo);
        btnStartCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermission()) {
                    openCamara();
                }
            }
        });
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {// 没有权限。
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                ToastUtil.showText("未获得相机权限");
            } else {
                // 申请授权。

                AndPermission.with(getActivity())
                        .requestCode(100)
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
                openCamara();
            }
        }

        @Override
        public void onFailed(int requestCode) {
            if (requestCode == 100) {
                LogUtil.e("申请失败");
            }
        }
    };

    private void openCamara() {
        String tempFileName = new Date().toString() + "temp.jpg";
        tempFile = new File(Environment.getExternalStorageDirectory() + "/wakeup", tempFileName);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("camerasensortype", 2); // 调用前置摄像头
        intent.putExtra("autofocus", true); // 自动对焦
        intent.putExtra("fullScreen", false); // 全屏
        intent.putExtra("showActionIcons", false);
        // 指定调用相机拍照后照片的存储路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, PHOTO_CARMERA);
    }

    public void showTodayRankDialog(final int rank) {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(R.layout.dialog_today_rank)
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
                        dialog.dismiss();

                    }
                })
                .create();
        dialog.show();
        tvTodayRank = (TextView) dialog.findViewById(R.id.tv_todayRank);
        tvTodayRank.setText("亲，今日第" + rank + "位签到");
    }

    @Override
    public void onStart() {
        super.onStart();
        shakeCount = 50;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager != null) {
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);             // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    public void onShakeSignSuccess(Integer rank) {
        if (tvCount != null) {
            tvCount.setText("签到成功");
        }
        if (rank != null) {
            showTodayRankDialog(rank);
        }


    }

    public void onShakeSignFail() {
        if (tvCount != null) {
            tvCount.setText("签到失败");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_CARMERA: {
                Glide.with(getContext())
                        .load(tempFile)
                        .into(ivPhoto);
                tvCameraImage.setText("选择成功");
            }
            break;
            default:
                break;
        }
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("云端验证中...");
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

    }

    public void showSignCount(int count) {
        cpbSignTime.setCurrentValues(count);
    }

    public void showUserInfo(UserPoint userPoint) {
        cpbSignTime.setCurrentValues(userPoint.getCount());
        tvUserName.setText(userPoint.getName());

        Glide.with(getContext())
                .load(userPoint.getHeadURL())
                .placeholder(R.drawable.head)
                .into(civHead);
        LogUtil.e(userPoint.getHeadURL());
        tvPoint.setText(userPoint.getAccumulatePoint() + "积分");
    }

    public void onGetWeatherInfo(Weather weather) {
        tvWeather.setText("苏州：" + weather.getData().getForecast().get(0).getType());
        tvTemperature.setText("实时温度：" + weather.getData().getWendu() + "℃");
    }

    public String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }
}
