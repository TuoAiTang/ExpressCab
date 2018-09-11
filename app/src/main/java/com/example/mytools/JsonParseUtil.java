package com.example.mytools;

import com.example.classforparse.BaseInfo;
import com.google.gson.Gson;

public class JsonParseUtil {

    public static BaseInfo parse(String jsonstr, BaseInfo bi){
        Gson gson = new Gson();
        bi =  gson.fromJson(jsonstr, bi.getClass());
        return bi;
    }
}
