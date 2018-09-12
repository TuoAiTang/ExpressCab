package com.example.classforparse.info;

import com.example.classforparse.body.LoginBody;

public class LoginInfo extends BaseInfo {

    private LoginBody body;

    public LoginBody getBody() {
        return body;
    }

    public void setBody(LoginBody body) {
        this.body = body;
    }
}
