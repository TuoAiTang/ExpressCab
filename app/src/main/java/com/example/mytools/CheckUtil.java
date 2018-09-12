package com.example.mytools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

        return msg;
    }
    public static String checkPassword(String password){
        String msg = OK;

        return msg;
    }
}
