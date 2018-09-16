package com.example.mytools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {

    public static String OK = "ok";

    public static Boolean checkDelieveryInput(Context context, int cell_type, String exp_code, String consignee_phone) {
        Boolean flag = true;
        String msg ;
        if (exp_code.equals("")) {
            msg = "单号不能为空";
            flag = false;
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            return flag;
        }
        if (!checkAccount(context, consignee_phone)) {
            flag = false;
            return flag;
        }
        if (cell_type == 0) {
            msg = "请选择箱体类型";
            flag = false;
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            return flag;
        }
        return flag;
    }

    public static Boolean checkLoginInput(Context context,  String account, String password){
        if(!checkNetwork(context)){
            return false;
        }
        if(!checkAccount(context, account)){
            return false;
        }
        if(!checkPassword(context, password)){
            return false;
        }
        return true;
    }

    public static Boolean checkRegisterInput(Context context, String account, String vercode, String password){
        if(!checkLoginInput(context, account, password)){
            return false;
        }
        if(!checkVercode(context, vercode)){
            return false;
        }
        return true;
    }

    public static Boolean checkNetwork(Context paramContext){
        String msg = OK;
        ConnectivityManager connectivityManager = (ConnectivityManager)
                paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isAvailable()){
            return true;
        }
        else{
            msg = "检查你的网络";
            Toast.makeText(paramContext, msg, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static Boolean checkAccount(Context context, String account){
        String msg = OK;
        Boolean flag = true;
        if(account.equals("")){
            msg = "账号不能为空";
            flag = false;
        }
        else{
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(account);
            if(!m.matches()){
                msg = "无效的手机号";
                flag = false;
            }
        }
        if(!flag)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        return flag;
    }

    public static Boolean checkPassword(Context context, String password){
        String msg = OK;
        Boolean flag = true;
        if(password.equals("")){
            msg = "密码不能为空";
            flag = false;
        }
        else{
            Pattern p = Pattern.compile("^[a-zA-Z0-9]{6,18}$");
            Matcher m = p.matcher(password);
            if(!m.matches()){
                msg = "密码不合法！请检查！";
                flag = false;
            }
        }
        if(!flag)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        return flag;
    }

    public static Boolean checkVercode(Context context, String vercode){
        String msg = OK;
        Boolean flag = true;
        if(vercode.equals("")) {
            msg = "验证码不能为空";
            flag = false;
        }
        Pattern p = Pattern.compile("\\d{6}");
        Matcher m = p.matcher(vercode);
        if(!m.matches()){
            msg = "验证码为6位数字";
            flag = false;
        }
        if(!flag)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        return flag;
    }
}
