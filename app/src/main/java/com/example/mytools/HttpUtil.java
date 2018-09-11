package com.example.mytools;


import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback, RequestBody rb) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(rb)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
