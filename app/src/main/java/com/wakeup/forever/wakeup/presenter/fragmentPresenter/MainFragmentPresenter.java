package com.wakeup.forever.wakeup.presenter.fragmentPresenter;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.app.App;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.model.DataManager.MicrosoftDataManager;
import com.wakeup.forever.wakeup.model.DataManager.UserDataManager;
import com.wakeup.forever.wakeup.model.DataManager.VersionDataManager;
import com.wakeup.forever.wakeup.model.bean.Emotion;
import com.wakeup.forever.wakeup.model.bean.FaceAnalyse;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.MicrosoftBody;
import com.wakeup.forever.wakeup.model.bean.TodayRank;
import com.wakeup.forever.wakeup.model.bean.UserPoint;
import com.wakeup.forever.wakeup.model.bean.VersionUpdate;
import com.wakeup.forever.wakeup.model.bean.Weather;
import com.wakeup.forever.wakeup.utils.GetImageFragmentUtil;
import com.wakeup.forever.wakeup.utils.GetImageUtils;
import com.wakeup.forever.wakeup.utils.ImageUtil;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.fragment.MainFragment;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by forever on 2016/8/17.
 */
public class MainFragmentPresenter extends BeamBasePresenter<MainFragment> {


    @Override
    protected void onCreate(@NonNull MainFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
    }


    public void signIn() {
        UserDataManager.getInstance().signIn(new BaseSubscriber<HttpResult<TodayRank>>(getView().getContext()) {
            @Override
            public void onSuccess(HttpResult<TodayRank> stringHttpResult) {
                getView().dismissProgressDialog();
                if (stringHttpResult.getResultCode() == 200) {
                    ToastUtil.showText("签到成功");
                    getView().onShakeSignSuccess(stringHttpResult.getData().getRank());
                } else {
                    getView().onShakeSignFail();
                    ToastUtil.showText(stringHttpResult.getMessage());
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().dismissProgressDialog();
                getView().onShakeSignFail();
            }
        });
    }


    public void initUserInfo() {
        UserDataManager.getInstance().getUserInfoWithSign(new BaseSubscriber<HttpResult<UserPoint>>(App.getGlobalContext()) {
            @Override
            public void onSuccess(HttpResult<UserPoint> userPointHttpResult) {
                if (userPointHttpResult.getResultCode() == 200) {
                    getView().showUserInfo(userPointHttpResult.getData());
                } else {
                    ToastUtil.showText(userPointHttpResult.getMessage());
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showText(e.getMessage());
            }
        });
    }

    public void checkUpdate() {
        try {
            final int versionCode = getView().getActivity().getPackageManager().getPackageInfo(getView().getActivity().getPackageName(), 0).versionCode;
            VersionDataManager.getInstance().getNewVersion(new Subscriber<HttpResult<VersionUpdate>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(HttpResult<VersionUpdate> versionUpdateHttpResult) {
                    if (versionUpdateHttpResult.getResultCode() == 200) {
                        if (versionUpdateHttpResult.getData().getVersionCode() > versionCode) {
                            getView().showUpdateDialog(versionUpdateHttpResult.getData());
                        }
                    }
                }
            });
        } catch (PackageManager.NameNotFoundException e) {

        }


    }

    public void camera(File imageFile) {
        getView().showProgressDialog();
        RequestBody imageHead = RequestBody.create(MediaType.parse("multipart/form-data"), ImageUtil.scal(imageFile.getAbsolutePath()));
        MicrosoftDataManager.getInstance().addEmotion(imageHead, new Subscriber<FaceAnalyse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().dismissProgressDialog();
                if (e instanceof HttpException) {
                    try {
                        LogUtil.e(((HttpException) e).response().errorBody().string());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }


            }

            @Override
            public void onNext(FaceAnalyse faceAnalyse) {
                if (faceAnalyse != null) {
                    if (faceAnalyse.getFaces() == null || (faceAnalyse.getFaces().size() != 1)) {
                        getView().dismissProgressDialog();
                        ToastUtil.showText("未找到人脸或屏幕中不只一张脸");

                    } else {
                        FaceAnalyse.FacesBean facesBean = faceAnalyse.getFaces().get(0);
                        if (facesBean.getAttributes().getSmile().getValue() < facesBean.getAttributes().getSmile().getThreshold()) {
                            getView().dismissProgressDialog();
                            LogUtil.e(facesBean.getAttributes().getSmile().getValue() + "微笑");
                            LogUtil.e(facesBean.getAttributes().getSmile().getThreshold() + "微笑阙值");
                            ToastUtil.showText("您的微笑度为" + (facesBean.getAttributes().getSmile().getValue()) + "%,不满足签到条件");
                        } else {
                            LogUtil.e(facesBean.getAttributes().getSmile().getThreshold() + "微笑阙值");
                            LogUtil.e(facesBean.getAttributes().getSmile().getValue() + "微笑");
                            signIn();
                        }
                    }
                }
            }
        });
    }

    public void getWeatherInfo(){
        MicrosoftDataManager.getInstance().getWeatherInfo(new Subscriber<Weather>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e.toString());
            }

            @Override
            public void onNext(Weather weather) {
                if(weather!=null){
                    getView().onGetWeatherInfo(weather);
                }
                else{
                    ToastUtil.showText("获取天气信息失败");
                }
            }
        });
    }
}
