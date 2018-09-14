package com.example.expresscab;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Entity.info.LoginInfo;
import com.example.mytools.GlobalData;
import com.example.mytools.HttpUtil;
import com.example.mytools.JsonParseUtil;
import com.example.mytools.CheckUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private Button check_login_btn;

    private Button find_password_btn;

    private EditText account_edit;

    private EditText password_edit;

    private LoginInfo login_info = null;

    private final  String TAG = "LoginActivity";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            login_info = (LoginInfo) msg.obj;

            if(login_info != null){
                switch (login_info.getCode()){
                    default:
                        Toast.makeText(LoginActivity.this, login_info.getMsg(),
                                Toast.LENGTH_LONG).show();
                        return;
                    case 0:
                        Toast.makeText(LoginActivity.this, "登陆成功！",
                                Toast.LENGTH_SHORT).show();
                        GlobalData.setUid(login_info.getBody().getId());
                        GlobalData.setSid(login_info.getBody().getSession().getSid());
                        Intent intent = new Intent(LoginActivity.this, FunctionActivity.class);
                        startActivity(intent);
                }
            }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        check_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = account_edit.getText().toString();
                String password = password_edit.getText().toString();
                Log.d(TAG, "account:" + account);
                Log.d(TAG, "password:" + password);
                //检查网络，账号，密码
                String check_network_msg = CheckUtil.checkNetwork(LoginActivity.this);
                String check_account_msg = CheckUtil.checkAccount(account);
                String check_password_msg = CheckUtil.checkPassword(password);
                if(check_network_msg != CheckUtil.OK){
                    Toast.makeText(LoginActivity.this, check_network_msg, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(check_account_msg != CheckUtil.OK){
                    Toast.makeText(LoginActivity.this, check_account_msg
                            , Toast.LENGTH_SHORT).show();
                    return;
                }
                if(check_password_msg != CheckUtil.OK){
                    Toast.makeText(LoginActivity.this, check_password_msg
                            , Toast.LENGTH_SHORT).show();
                    return;
                }
                check_login(account, password);
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

    public void initView(){

        check_login_btn = findViewById(R.id.login_check_btn);
        find_password_btn = findViewById(R.id.find_password_btn);
        account_edit = findViewById(R.id.account_edit_text);
        password_edit = findViewById(R.id.password_edit_text);

    }

    public void check_login(String account, String password){

        String api_url = "http://101.200.89.170:9000/capp/login/normal";
        RequestBody requestBody = new FormBody.Builder()
                .add("phone", account)
                .add("password", password)
                .build();
        HttpUtil.sendOkHttpRequest(api_url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "异常信息：\n" + e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);
                Log.d(TAG, "Type of LoginInfo" + LoginInfo.class);
                login_info = JsonParseUtil.parseForLogin(responseData);
                Message message = new Message();
                message.obj = login_info;
                handler.sendMessage(message);
            }
        }, requestBody);
    }
}


