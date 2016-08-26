package com.wakeup.forever.wakeup.model.DataManager;

import com.wakeup.forever.wakeup.model.bean.User;

import org.litepal.crud.DataSupport;

/**
 * Created by forever on 2016/8/26.
 */
public class UserCacheManager {

    public static User getUser(){
       return  DataSupport.findFirst(User.class);
    }

   public  static void saveUser(User user){
       if(DataSupport.findFirst(User.class)!=null){
           user.update(1);    //用户信息只存一条
       }
       else{
           user.saveThrows();
       }
   }
}
