package com.wakeup.forever.wakeup.view.fragment;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.http.download.DownloadUtils;
import com.wakeup.forever.wakeup.model.DataManager.ActivityManager;
import com.wakeup.forever.wakeup.model.bean.VersionUpdate;
import com.wakeup.forever.wakeup.presenter.fragmentPresenter.SettingFragmentPresenter;
import com.wakeup.forever.wakeup.utils.CacheDataUtil;
import com.wakeup.forever.wakeup.utils.PrefUtils;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.activity.FindPasswordActivity;
import com.wakeup.forever.wakeup.view.activity.LoginActivity;
import com.wakeup.forever.wakeup.widget.EditDialogBuilder;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */

@RequiresPresenter(SettingFragmentPresenter.class)
public class SettingFragment extends BeamFragment<SettingFragmentPresenter> {
    @Bind(R.id.ib_back)
    ImageButton ibBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.rl_update_password)
    RelativeLayout rlUpdatePassword;
    @Bind(R.id.switch_wifi_auto)
    SwitchCompat switchWifiAuto;
    @Bind(R.id.rl_auto_update)
    RelativeLayout rlAutoUpdate;
    @Bind(R.id.rl_feedback)
    RelativeLayout rlFeedback;
    @Bind(R.id.rl_star)
    RelativeLayout rlStar;
    @Bind(R.id.rl_login_out)
    RelativeLayout rlLoginOut;
    @Bind(R.id.rl_update)
    RelativeLayout rlUpdate;
    @Bind(R.id.tv_cache)
    TextView tvCache;
    @Bind(R.id.rl_clearCache)
    RelativeLayout rlClearCache;

    private int lastProgress = 0;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        try {
            tvCache.setText(CacheDataUtil.getTotalCacheSize(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.rl_login_out)
    public void onClick() {
        PrefUtils.setString(getContext(), GlobalConstant.TOKEN, "");
        startActivity(new Intent(getContext(), LoginActivity.class));
        ActivityManager.finishAll();
    }


    @OnClick({R.id.ib_back, R.id.tv_title, R.id.rl_title, R.id.rl_update_password, R.id.switch_wifi_auto, R.id.rl_auto_update, R.id.rl_feedback, R.id.rl_star, R.id.rl_update,R.id.rl_clearCache})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                getActivity().finish();
                break;
            case R.id.tv_title:
                break;
            case R.id.rl_title:
                break;
            case R.id.rl_clearCache:
                clearCache();
                break;
            case R.id.rl_update:
                checkUpdate();
                break;
            case R.id.rl_update_password:
                updatePassword();
                break;
            case R.id.switch_wifi_auto:
                break;
            case R.id.rl_auto_update:
                break;
            case R.id.rl_feedback:
                feedback();
                break;
            case R.id.rl_star:
                star();
                break;
        }
    }

    private void clearCache() {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setTitle("缓存详情")
                .setMessage("是否确定清理缓存")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CacheDataUtil.clearAllCache(getContext());
                        tvCache.setText("0k");
                    }
                })
                .create();
        dialog.show();
    }

    public void updatePassword() {
        Intent i = new Intent(getContext(), FindPasswordActivity.class);
        startActivity(i);
    }

    public void star() {
        try {
            Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ToastUtil.showText("没有找到应用市场");
        }
    }

    public void feedback() {
        EditDialogBuilder builder = new EditDialogBuilder(getContext());
        builder.setDialogTitle("告诉我们你的想法：").setPositiveButtonListener(new EditDialogBuilder.PositiveButtonClickListener() {
            @Override
            public void onClick(String content) {
                if (TextUtils.isEmpty(content.trim())) {
                    ToastUtil.showText("内容不能為空");
                } else {
                    ToastUtil.showText("感谢您的反馈！");
                }
            }
        }).build().show();
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

    public void showAlreadyNewDialog(){
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setTitle("更新信息")
                .setMessage("已是最新版本了，亲")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        dialog.show();
    }

    public void checkUpdate() {
        getPresenter().checkUpdate();
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
        Intent install = new Intent();
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setAction(Intent.ACTION_VIEW);
        install.setDataAndType(Uri.fromFile(mFile), "application/vnd.android.package-archive");
        startActivity(install);
    }


}
