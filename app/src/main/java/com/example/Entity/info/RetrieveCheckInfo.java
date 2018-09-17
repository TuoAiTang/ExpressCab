package com.example.Entity.info;

import com.google.gson.JsonElement;

public class RetrieveCheckInfo {

    private int code;

    private String msg;

    private JsonElement body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JsonElement getBody() {
        return body;
    }

    public void setBody(JsonElement body) {
        this.body = body;
    }
}
