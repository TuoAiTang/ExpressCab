package com.example.mytools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

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
        if(account.equals(""))
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
        if(password.equals(""))
            msg = "密码不能为空！";
        else{
            Pattern p = Pattern.compile("^[a-zA-Z0-9]{6,18}$");
            Matcher m = p.matcher(password);
            if(!m.matches())
                msg = "密码不合法！请检查！";
        }
        return msg;
    }

    public static Boolean checkInput(Context context, int cell_type, String exp_code, String consignee_phone) {
        Boolean flag = true;
        String msg = "检查输入";
        if (exp_code.equals("")) {
            msg = "单号不能为空";
            flag = false;
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            return flag;
        }
        if ((consignee_phone.equals(""))) {
            msg = "联系人手机号不能为空";
            flag = false;
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            return flag;
        }
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(consignee_phone);
        if(!m.matches()){
            msg = "无效的手机号";
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
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
}
