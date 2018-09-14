package com.example.mytools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {

    public static String OK = "ok";

    public static String checkNetwork(Context paramContext){
        String msg = OK;
        ConnectivityManager connectivityManager = (ConnectivityManager)
                paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isAvailable()){
            msg = OK;
            return msg;
        }
        else{
            msg = "检查你的网络！";
            return msg;
        }
    }

    public static String checkAccount(String account){
        String msg = OK;
        if(account == "")
            msg = "账号不能为空！";
        else{
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(account);
            if(!m.matches())
                msg = "账号不合法！请检查！";
        }
        return msg;
    }
    public static String checkPassword(String password){
        String msg = OK;
        if(password == "")
            msg = "密码不能为空！";
        else{
            Pattern p = Pattern.compile("^[a-zA-Z0-9]{6,18}$");
            Matcher m = p.matcher(password);
            if(!m.matches())
                msg = "密码不合法！请检查！";
        }
        return msg;
    }
}
