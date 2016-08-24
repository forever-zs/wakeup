package com.wakeup.forever.wakeup.utils;

import java.util.regex.Pattern;

/**
 * Created by forever on 2016/8/20.
 */
public class CheckUtil {
    public static boolean checkPhone(String phone){
        if(phone.length()!=11){
            return false;
        }
        else{
            Pattern p=Pattern.compile("^1[3|4|5|7|8]\\d{9}$");
            if(p.matcher(phone).matches()){
                return true;
            }
            else{
                return false;
            }
        }
    }

    //验证密码，6~20位字母加数字段的组合
    public static boolean checkPassword(String password){
        Pattern p=Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");
        if(p.matcher(password).matches()){
            return true;
        }
        else{
            return  false;
        }
    }


}
