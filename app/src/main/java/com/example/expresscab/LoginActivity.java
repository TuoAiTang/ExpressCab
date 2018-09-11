package com.example.expresscab;

import android.content.Intent;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.classforparse.BaseInfo;
import com.example.mytools.HttpUtil;
import com.example.mytools.JsonParseUtil;
import com.example.mytools.NetworkUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private Button check_login_btn;

    private Button find_password_btn;

    private EditText account_edit;

    private EditText password_edit;

    private BaseInfo login_info = null;

    private final  String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        check_login_btn = findViewById(R.id.login_check_btn);
        find_password_btn = findViewById(R.id.find_password_btn);
        account_edit = findViewById(R.id.account_edit_text);
        password_edit = findViewById(R.id.password_edit_text);

        check_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //没有网络
                if(!NetworkUtil.isNetworkAvailable(LoginActivity.this)){
                    Toast.makeText(LoginActivity.this, "检查你的网络！", Toast.LENGTH_SHORT).show();
                    return;
                }
                String account = account_edit.getText().toString();
                String password = password_edit.getText().toString();
                Log.d(TAG, "account:" + account);
                Log.d(TAG, "password:" + password);
                check_login(account, password);

                if(login_info != null){
                    Toast.makeText(LoginActivity.this, "返回msg:" + login_info.getMsg(),
                            Toast.LENGTH_LONG).show();
                    Log.d(TAG, "login_info_message" + login_info.getMsg());
                    Intent intent = new Intent(LoginActivity.this, FunctionActivity.class);
                    startActivity(intent);
                }
            }
        });

        find_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
                startActivity(intent);
            }
        });


    }

    public void check_login(String account, String password){
        String api_url = "http://101.200.89.170:9000/capp/login/normal";
        RequestBody requestBody = new FormBody.Builder()
                                    .add("phone", account)
                                    .add("password", password)
                                    .build();
        HttpUtil.sendOkHttpRequest(api_url, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responsData = response.body().string();
                Log.d(TAG, "onResponse: " + responsData);
                BaseInfo login_info = new BaseInfo();
                login_info = JsonParseUtil.parse(responsData, login_info);
                Log.d(TAG, "onResponse: " + login_info.getMsg());
                Log.d(TAG, "onResponse: " + login_info.getBody());
                Log.d(TAG, "onResponse: " + login_info.getCode());
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "sendOkHttpRequestFailure!");
                Log.d(TAG, "异常信息：\n" + e.getMessage());
                Log.d(TAG, "e.toString" + e.toString());
                e.printStackTrace();
                login_info = null;
            }
        }, requestBody);
    }

}
