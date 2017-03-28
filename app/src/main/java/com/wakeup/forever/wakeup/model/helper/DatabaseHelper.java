package com.wakeup.forever.wakeup.model.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.wakeup.forever.wakeup.model.bean.CommonShare;
import com.wakeup.forever.wakeup.model.bean.CommonShareComment;
import com.wakeup.forever.wakeup.model.bean.CommonShareLike;
import com.wakeup.forever.wakeup.model.bean.Share;
import com.wakeup.forever.wakeup.model.bean.User;
import com.wakeup.forever.wakeup.utils.LogUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by forever on 2016/9/3.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TABLE_NAME = "wakeup.db";
    private Map<String, Dao> daoList = new HashMap<String, Dao>();

    private DatabaseHelper(Context context)
    {
        super(context, TABLE_NAME, null, 3);
    }

    public DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {


        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Share.class);
            TableUtils.createTable(connectionSource, CommonShare.class);
            TableUtils.createTable(connectionSource, CommonShareComment.class);
            TableUtils.createTable(connectionSource, CommonShareLike.class);
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtil.e(e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Share.class, true);
            TableUtils.dropTable(connectionSource, CommonShare.class, true);
            TableUtils.dropTable(connectionSource, CommonShareComment.class, true);
            TableUtils.dropTable(connectionSource, CommonShareLike.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onCreate(database, connectionSource);
    }

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getHelper(Context context)
    {
        context = context.getApplicationContext();
        if (instance == null)
        {
            synchronized (DatabaseHelper.class)
            {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }

        return instance;
    }

    public synchronized Dao getDao(Class clazz) throws SQLException
    {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daoList.containsKey(className))
        {
            dao = daoList.get(className);
        }
        if (dao == null)
        {
            dao = super.getDao(clazz);
            daoList.put(className, dao);
        }
        return dao;
    }

    @Override
    public void close()
    {
        super.close();

        for (String key : daoList.keySet())
        {
            Dao dao = daoList.get(key);
            dao = null;
        }
    }



}
