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
import com.example.mytools.APIUtil;
import com.example.mytools.GlobalData;
import com.example.mytools.HttpUtil;
import com.example.mytools.JsonParseUtil;
import com.example.mytools.CheckUtil;
import com.example.mytools.MyToastUtil;

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
                        MyToastUtil.getCustomToast(LoginActivity.this,"登录成功",
                        Toast.LENGTH_SHORT, R.drawable.login_ok).show();
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
                //检查网络，账号，密码
                if(CheckUtil.checkLoginInput(LoginActivity.this, account, password))
                    APIUtil.invokeLoginAPI(handler, account, password);
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

}


