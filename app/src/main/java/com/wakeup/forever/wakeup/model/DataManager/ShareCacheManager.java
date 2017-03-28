package com.wakeup.forever.wakeup.model.DataManager;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.wakeup.forever.wakeup.model.bean.CommonShare;
import com.wakeup.forever.wakeup.model.bean.CommonShareComment;
import com.wakeup.forever.wakeup.model.bean.CommonShareLike;
import com.wakeup.forever.wakeup.model.bean.Share;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.model.helper.DatabaseHelper;
import com.wakeup.forever.wakeup.utils.LogUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by forever on 2016/8/28.
 */
public class ShareCacheManager {

    private Context context;
    private Dao<Share, Integer> shareDao;
    private Dao<User, Integer> userDao;
    private Dao<CommonShare,Integer> commonShareDao;
    private Dao<CommonShareLike,Integer> commonShareLikeDao;
    private Dao<CommonShareComment,Integer> commonShareCommentDao;
    private DatabaseHelper helper;
    private static ShareCacheManager shareCacheManager;

    private ShareCacheManager(Context context){
        this.context=context;
        helper=DatabaseHelper.getHelper(context);
        try {
            userDao=helper.getDao(User.class);
            shareDao=helper.getDao(Share.class);
            commonShareDao=helper.getDao(CommonShare.class);
            commonShareLikeDao=helper.getDao(CommonShareLike.class);
            commonShareCommentDao=helper.getDao(CommonShareComment.class);
        } catch (SQLException e) {
            LogUtil.e(e.getMessage());
        }

    }

    public static ShareCacheManager getInstance(Context context){
        if(shareCacheManager==null){
            shareCacheManager=new ShareCacheManager(context);
        }
        return  shareCacheManager;
    }
    public  void saveShareList(final ArrayList<Share> shareList){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(Share share:shareList){
                    try {
                        shareDao.createOrUpdate(share);
                    } catch (SQLException e) {
                        LogUtil.e(e.getMessage());
                    }
                }
            }
        }).start();

    }

    public  ArrayList<Share> getLastShareList(){
        ArrayList<Share> shareList=new ArrayList<Share>();
        try {
            List<Share> shares=shareDao.queryBuilder().limit(10l).orderBy("id",false).query();
            shareList.addAll(shares);
        } catch (SQLException e) {
            LogUtil.e(e.getMessage());
        }
        return shareList;
    }

    public  void saveCommonShareList(final ArrayList<CommonShare> shareList){
        try {
            commonShareCommentDao.deleteBuilder().delete();
            commonShareLikeDao.deleteBuilder().delete();
            commonShareDao.deleteBuilder().delete();
        } catch (SQLException e) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(CommonShare commonShare:shareList){
                    try {
                        commonShareDao.createOrUpdate(commonShare);
                        if(commonShare.getUser()!=null){
                            userDao.createOrUpdate(commonShare.getUser());
                        }
                        if(commonShare.getLikedList()!=null){
                            for(CommonShareLike commonShareLike:commonShare.getLikedList()){
                                commonShareLikeDao.createOrUpdate(commonShareLike);
                            }
                        }

                        if(commonShare.getCommentList()!=null){
                            for(CommonShareComment commonShareComment:commonShare.getCommentList()){
                                commonShareCommentDao.createOrUpdate(commonShareComment);
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public  ArrayList<CommonShare> getLastCommonShareList(){
        ArrayList<CommonShare> commonShareList = new ArrayList<CommonShare>();
        try {
            commonShareList.addAll(commonShareDao.queryBuilder().limit(10l).orderBy("id",false).query());
            for(int i=0;i<commonShareList.size();i++){
                List<CommonShareLike> commonShareLikes=commonShareLikeDao.queryBuilder().where().eq("common_share_id",commonShareList.get(i).getId()).query();
                ArrayList<CommonShareLike> commonShareLikeArrayList=new ArrayList<CommonShareLike>();
                commonShareLikeArrayList.addAll(commonShareLikes);
                commonShareList.get(i).setLikedList(commonShareLikeArrayList);      //填充用户点赞数据

                List<CommonShareComment> commonShareComments=commonShareCommentDao.queryBuilder().where().eq("common_share_id",commonShareList.get(i).getId()).query();
                ArrayList<CommonShareComment> commonShareCommentArrayList=new ArrayList<CommonShareComment>();
                commonShareCommentArrayList.addAll(commonShareComments);
                commonShareList.get(i).setCommentList(commonShareCommentArrayList);      //填充用户评论数据

                User user=userDao.queryBuilder().where().eq("phone",commonShareList.get(i).getUserPhone()).queryForFirst();
                if(user==null){
                    user=new User();
                }
                commonShareList.get(i).setUser(user);              //填充用户数据
            }
        } catch (SQLException e) {
            LogUtil.e(e.getMessage());
        }
        return commonShareList;
    }
}
