package com.wakeup.forever.wakeup.presenter.fragmentPresenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.jude.beam.expansion.BeamBasePresenter;
import com.wakeup.forever.wakeup.app.App;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.UserCacheManager;
import com.wakeup.forever.wakeup.model.DataManager.UserDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.utils.GetImageFragmentUtil;
import com.wakeup.forever.wakeup.utils.GetImageUtils;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.PrefUtils;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.fragment.UserCenterFragment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by forever on 2016/8/22.
 */
public class UserCenterFragmentPresenter extends BeamBasePresenter<UserCenterFragment> {

    private UserCenterFragment userCenterFragment;

    private File tempFile;
    private GetImageFragmentUtil getImageUtils;

    @Override
    protected void onCreate(@NonNull UserCenterFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        userCenterFragment = getView();
    }

    public void initData() {
        /*
            从数据库里面拿到用户信息缓存
         */
        String token= PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN,"");
        final User user = UserCacheManager.getInstance(getView().getContext()).getUser();
        if (user != null) {
            userCenterFragment.showUserInfo(user);
        }

        UserDataManager.getInstance().getUserInfo(new BaseSubscriber<HttpResult<User>>(userCenterFragment.getActivity()) {
            @Override
            public void onSuccess(HttpResult<User> userHttpResult) {
                if (userHttpResult.getResultCode() == 200) {
                    userCenterFragment.showUserInfo(userHttpResult.getData());
                    UserCacheManager.getInstance(getView().getContext()).updateUser(userHttpResult.getData());
                    PrefUtils.setString(getView().getContext(),GlobalConstant.USER_ID,userHttpResult.getData().getId()+"");
                } else {
                    ToastUtil.showText("未知错误");
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showText("网络连接错误");
            }
        });
        tempFile = new File(Environment.getExternalStorageDirectory(), GetImageUtils.getPhotoFileName());
        getImageUtils = new GetImageFragmentUtil(userCenterFragment, tempFile, userCenterFragment.getCimHead());
    }

    public void disposeResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GetImageUtils.PHOTO_CARMERA:
                getImageUtils.startPhotoZoom(Uri.fromFile(tempFile), 300);
                break;
            case GetImageUtils.PHOTO_PICK:
                if (null != data) {
                    getImageUtils.startPhotoZoom(data.getData(), 300);
                }
                break;
            case GetImageUtils.PHOTO_CUT:
                if (null != data) {
                    getImageUtils.setPicToView(data);
                    uploadHeadImage();
                }
                break;

            default:
                break;
        }
    }

    private void uploadHeadImage() {
        userCenterFragment.showProgressDialog();
        RequestBody imageHead = RequestBody.create(MediaType.parse("multipart/form-data"), tempFile);
        UserDataManager.getInstance().uploadHeadUrl(imageHead, new BaseSubscriber<HttpResult<User>>(userCenterFragment.getActivity()) {
            @Override
            public void onSuccess(HttpResult<User> userHttpResult) {
                userCenterFragment.dismissProgressDialog();
                if (userHttpResult.getResultCode() == 200) {
                    userCenterFragment.showSnackBar("上传成功");
                } else {
                    userCenterFragment.showSnackBar(userHttpResult.getMessage());
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e.getMessage());
                userCenterFragment.dismissProgressDialog();
                userCenterFragment.showSnackBar("床君崩溃了，整个人都不好了");
            }
        });
    }

    public GetImageFragmentUtil getImageUtil() {
        return getImageUtils;
    }

    public void updateUserInfo(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        if(user.getName()!=null){
            map.put("name", user.getName());
        }
        if(user.getSex()!=null){
            map.put("sex", user.getSex());
        }
        if(user.getSignature()!=null){
            map.put("signature", user.getSignature());
        }
        if(user.getCampus()!=null){
            map.put("campus", user.getCampus());
        }
        if(user.getBirth()!=null){
            map.put("birth", user.getBirth());
        }

        UserDataManager.getInstance().updateUserInfo(map, new BaseSubscriber<HttpResult<User>>(userCenterFragment.getActivity()) {
            @Override
            public void onSuccess(HttpResult<User> userHttpResult) {
                if (userHttpResult.getResultCode() == 200) {
                    userCenterFragment.showSnackBar("修改成功");
                } else {
                    userCenterFragment.showSnackBar(GlobalConstant.ERROR_MESSAGE);
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e.getMessage());
                userCenterFragment.showSnackBar(GlobalConstant.ERROR_MESSAGE);
            }
        });
    }
}
