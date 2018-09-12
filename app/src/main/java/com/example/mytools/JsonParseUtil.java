package com.example.mytools;

import com.example.classforparse.info.LoginInfo;
import com.google.gson.Gson;

public class JsonParseUtil {

    public static LoginInfo parseForLogin(String jsonstr){
        Gson gson = new Gson();
        LoginInfo loginInfo=  gson.fromJson(jsonstr, LoginInfo.class);
        return loginInfo;
    }

}
