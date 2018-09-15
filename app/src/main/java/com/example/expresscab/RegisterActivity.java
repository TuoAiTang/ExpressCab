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
import com.example.Entity.info.RegisterCheckInfo;
import com.example.Entity.info.RegisterSendInfo;
import com.example.mytools.CheckUtil;
import com.example.mytools.HttpUtil;
import com.example.mytools.JsonParseUtil;
import com.example.mytools.TimeCount;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private RegisterSendInfo registerSendInfo;
    private RegisterCheckInfo registerCheckInfo;

    private String account;
    private String ver_code;
    private String password;

    private EditText account_ed;
    private EditText ver_code_ed;
    private EditText password_ed;

    private Button send_vercode_btn;
    private Button register_btn;

    private TimeCount time;

    private String TAG = "注册";

    private Handler send_pcode_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            registerSendInfo = (RegisterSendInfo)msg.obj;
            if(registerSendInfo.getCode() == 0){
                Toast.makeText(RegisterActivity.this, "发送成功",Toast.LENGTH_SHORT).show();
                RegisterActivity.this.time.start();
            }else{
                Toast.makeText(RegisterActivity.this, registerSendInfo.getMsg(),Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Handler register_check_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            registerCheckInfo = (RegisterCheckInfo)msg.obj;
            if(registerCheckInfo.getCode() == 0){
                Toast.makeText(RegisterActivity.this, "注册成功\n\n去登录吧！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(RegisterActivity.this, registerCheckInfo.getMsg(),Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        send_vercode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account = account_ed.getText().toString();
                String check_account_msg = CheckUtil.checkAccount(account);
                if(check_account_msg == CheckUtil.OK){
                    invokeRegisterSendVercodeAPI();
                }else{
                    Toast.makeText(RegisterActivity.this, check_account_msg
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account = account_ed.getText().toString();
                String check_account_msg = CheckUtil.checkAccount(account);
                ver_code = ver_code_ed.getText().toString();
                String check_ver_code_msg = CheckUtil.checkVercode(ver_code);
                password = password_ed.getText().toString();
                String check_password_msg = CheckUtil.checkPassword(password);
                if(check_account_msg != CheckUtil.OK){
                    Toast.makeText(RegisterActivity.this, check_account_msg
                            , Toast.LENGTH_SHORT).show();
                    return;
                }
                if(check_ver_code_msg != CheckUtil.OK){
                    Toast.makeText(RegisterActivity.this, check_ver_code_msg
                            , Toast.LENGTH_SHORT).show();
                    return;
                }
                if(check_password_msg != CheckUtil.OK){
                    Toast.makeText(RegisterActivity.this, check_password_msg
                            , Toast.LENGTH_SHORT).show();
                    return;
                }
                invokeRegisterCheckAPI();
            }
        });
    }

    void initView(){
        account_ed = findViewById(R.id.account_edit_text);
        ver_code_ed = findViewById(R.id.vercode_edit_text);
        password_ed = findViewById(R.id.password_edit_text);
        send_vercode_btn = findViewById(R.id.btn_register_getvercode);
        register_btn = findViewById(R.id.register_btn);
        time = new TimeCount(60000L, 1000L, this.send_vercode_btn);
    }

    void invokeRegisterSendVercodeAPI(){
        String api_url = "http://101.200.89.170:9000/capp/register/send_pcode";
        RequestBody requestBody = new FormBody.Builder()
                .add("phone", account)
                .build();

        HttpUtil.sendOkHttpRequest(api_url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);
                registerSendInfo = JsonParseUtil.parseForRegisterSend(responseData);
                Message message = new Message();
                message.obj = registerSendInfo;
                send_pcode_handler.sendMessage(message);
            }
        }, requestBody);
    }

    void invokeRegisterCheckAPI(){
        String api_url = "http://101.200.89.170:9000/capp/register/phone";
        RequestBody requestBody = new FormBody.Builder()
                .add("phone", account)
                .add("pcode", ver_code)
                .add("password",password)
                .build();
        HttpUtil.sendOkHttpRequest(api_url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);
                registerCheckInfo = JsonParseUtil.parseForRegisterCheck(responseData);
                Message message = new Message();
                message.obj = registerCheckInfo;
                register_check_handler.sendMessage(message);
            }
        }, requestBody);
    }
}
