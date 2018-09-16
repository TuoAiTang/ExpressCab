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

import com.example.Entity.info.RegiResetCheckInfo;
import com.example.Entity.info.SendInfo;
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

    private SendInfo registerSendInfo;
    private RegiResetCheckInfo registerCheckInfo;

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
            registerSendInfo = (SendInfo)msg.obj;
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
            registerCheckInfo = (RegiResetCheckInfo)msg.obj;
            if(registerCheckInfo.getCode() == 0){
                Toast.makeText(RegisterActivity.this, "注册成功,去登录吧！",Toast.LENGTH_SHORT).show();
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
                if(CheckUtil.checkAccount(RegisterActivity .this, account)){
                    invokeRegisterSendVercodeAPI();
                }
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account = account_ed.getText().toString();
                ver_code = ver_code_ed.getText().toString();
                password = password_ed.getText().toString();
                if(CheckUtil.checkRegisterInput(RegisterActivity.this, account,
                        ver_code, password)){
                    invokeRegisterCheckAPI();
                }
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
                registerSendInfo = JsonParseUtil.parseForSend(responseData);
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
                registerCheckInfo = JsonParseUtil.parseForRegiResetCheck(responseData);
                Message message = new Message();
                message.obj = registerCheckInfo;
                register_check_handler.sendMessage(message);
            }
        }, requestBody);
    }
}
