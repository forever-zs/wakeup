package com.wakeup.forever.wakeup.presenter.fragmentPresenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jude.beam.expansion.BeamBasePresenter;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.wakeup.forever.wakeup.base.BaseSubscriber;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.DataManager.UserCacheManager;
import com.wakeup.forever.wakeup.model.DataManager.UserDataManager;
import com.wakeup.forever.wakeup.model.bean.HttpResult;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.utils.GetImageUtils;
import com.wakeup.forever.wakeup.utils.ImageUtil;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.ToastUtil;
import com.wakeup.forever.wakeup.view.fragment.UserCenterFragment;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by forever on 2016/8/22.
 */
public class UserCenterFragmentPresenter extends BeamBasePresenter<UserCenterFragment> {

    private UserCenterFragment userCenterFragment;

    private File tempFile;
    GetImageUtils getImageUtils;

    @Override
    protected void onCreate(@NonNull UserCenterFragment view, Bundle savedState) {
        super.onCreate(view, savedState);
        userCenterFragment=getView();
    }

    public void initData(){
        /*
            从数据库里面拿到用户信息缓存
         */
        User user= UserCacheManager.getUser();
        if(user!=null){
            userCenterFragment.showUserInfo(user);
            LogUtil.e("id:"+user.getId());
        }

        UserDataManager.getInstance().getUserInfo(new BaseSubscriber<HttpResult<User>>(userCenterFragment.getActivity()) {
            @Override
            public void onSuccess(HttpResult<User> userHttpResult) {
                if(userHttpResult.getResultCode()==200){
                    userCenterFragment.showUserInfo(userHttpResult.getData());
                    if(DataSupport.findFirst(User.class)!=null){
                        userHttpResult.getData().update(1);
                    }
                    else{
                        userHttpResult.getData().saveThrows();
                    }
                }
                else{
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
        getImageUtils=new GetImageUtils(userCenterFragment,tempFile,userCenterFragment.getCimHead());
    }

    public void disposeResult(int requestCode, int resultCode, Intent data){
        switch (requestCode) {
            case GetImageUtils.PHOTO_CARMERA:
                Log.e("zs","PHOTO_CARMERA");
                getImageUtils.startPhotoZoom(Uri.fromFile(tempFile), 300);
                break;
            case GetImageUtils.PHOTO_PICK:
                Log.e("zs","PHOTO_PICK");
                if (null != data) {
                    getImageUtils.startPhotoZoom(data.getData(), 300);
                }
                break;
            case GetImageUtils.PHOTO_CUT:
                Log.e("zs","PHOTO_CUT");
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
        tempFile=ImageUtil.saveBitmapFile(ImageUtil.getImage(tempFile.getAbsolutePath()));
        RequestBody imageHead=RequestBody.create(MediaType.parse("multipart/form-data"),tempFile);
        UserDataManager.getInstance().uploadHeadUrl(imageHead, new BaseSubscriber<HttpResult<User>>(userCenterFragment.getActivity()) {
            @Override
            public void onSuccess(HttpResult<User> userHttpResult) {
                userCenterFragment.dismissProgressDialog();
                if(userHttpResult.getResultCode()==200){
                    userCenterFragment.showSnackBar("上传成功");
                }
                else{
                    userCenterFragment.showSnackBar(userHttpResult.getMessage());
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                userCenterFragment.dismissProgressDialog();
                userCenterFragment.showSnackBar("床君崩溃了，整个人都不好了");
            }
        });
    }

    public GetImageUtils getImageUtil(){
        return  getImageUtils;
    }

    public void updateUserInfo(User user){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("name",user.getName());
        map.put("sex",user.getSex());
        map.put("signature",user.getSignature());
        map.put("campus",user.getCampus());
        map.put("birth",user.getBirth());
        UserDataManager.getInstance().updateUserInfo(map, new BaseSubscriber<HttpResult<User>>(userCenterFragment.getActivity()) {
            @Override
            public void onSuccess(HttpResult<User> userHttpResult) {
                if(userHttpResult.getResultCode()==200){
                    userCenterFragment.showSnackBar("修改成功");
                }
                else{
                    userCenterFragment.showSnackBar(GlobalConstant.ERROR_MESSAGE);
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                userCenterFragment.showSnackBar(GlobalConstant.ERROR_MESSAGE);
            }
        });
    }
}
