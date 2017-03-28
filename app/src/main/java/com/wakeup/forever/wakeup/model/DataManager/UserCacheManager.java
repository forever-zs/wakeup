package com.wakeup.forever.wakeup.model.DataManager;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.wakeup.forever.wakeup.app.App;
import com.wakeup.forever.wakeup.config.GlobalConstant;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.model.helper.DatabaseHelper;
import com.wakeup.forever.wakeup.utils.LogUtil;
import com.wakeup.forever.wakeup.utils.PrefUtils;

import java.sql.SQLException;

/**
 * Created by forever on 2016/8/26.
 */
public class UserCacheManager {

    private Context context;
    private Dao<User, Integer> userDao;
    private DatabaseHelper helper;
    private static UserCacheManager userCacheManager;

    public UserCacheManager(Context context) {
        this.context = context;
        helper = DatabaseHelper.getHelper(context);
        try {
            userDao = helper.getDao(User.class);
        } catch (SQLException e) {
            LogUtil.e(e.getMessage());
        }
    }

    public static UserCacheManager getInstance(Context context) {
        if (userCacheManager == null) {
            userCacheManager = new UserCacheManager(context);
        }
        return userCacheManager;
    }

    public User getUser() {
        int userId = Integer.parseInt(PrefUtils.getString(App.getGlobalContext(), GlobalConstant.USER_ID, "3"));

        User user = null;
        try {
            user = userDao.queryBuilder().where().eq("id", userId).queryForFirst();
        } catch (SQLException e) {
            LogUtil.e(e.getMessage());
        }
        if (user == null) {
            user = new User();
        }
        LogUtil.e(user.toString());
        return user;
    }

    public void saveUser(User user) {
        try {
            userDao.queryRaw("delete from tb_user");
            userDao.create(user);
        } catch (SQLException e) {
        }
    }

    public void updateUser(User user) {
        String token = PrefUtils.getString(App.getGlobalContext(), GlobalConstant.TOKEN, "");
        try {
            user.setToken(token);
            userDao.update(user);
        } catch (SQLException e) {
        }
    }
}
