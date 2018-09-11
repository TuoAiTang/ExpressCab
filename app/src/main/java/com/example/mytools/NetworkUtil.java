package com.example.mytools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    public static boolean isNetworkAvailable(Context paramContext){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isAvailable()){
            return true;
        }
        else
            return false;
    }
}
