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
import com.example.mytools.APIUtil;
import com.example.mytools.CheckUtil;
import com.example.mytools.TimeCount;

public class FindPasswordActivity extends AppCompatActivity {

    private String TAG = "找回密码";

    private String account;
    private String vercode;
    private String password;

    private EditText ed_account;
    private EditText ed_vercode;
    private EditText ed_password;

    private Button btn_send_vercode;
    private Button btn_reset_password;

    private SendInfo resetSendInfo;
    private RegiResetCheckInfo resetCheckInfo;
    private TimeCount time;

    private Handler send_pcode_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            resetSendInfo = (SendInfo)msg.obj;
            if(resetSendInfo.getCode() == 0){
                Toast.makeText(FindPasswordActivity.this, "发送成功",Toast.LENGTH_SHORT).show();
                //启动计时器，开始倒计时
                FindPasswordActivity.this.time.start();
            }else{
                Toast.makeText(FindPasswordActivity.this, resetSendInfo.getMsg(),Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Handler reset_check_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            resetCheckInfo = (RegiResetCheckInfo)msg.obj;
            Log.d(TAG, "handleMessage: " + resetCheckInfo.getMsg());
            Log.d(TAG, "handleMessage: " + resetCheckInfo.toString());
            Log.d(TAG, "handleMessage: " + resetCheckInfo.getCode());
            if(resetCheckInfo.getCode() == 0){
                Toast.makeText(FindPasswordActivity.this, "重置成功,去登录吧！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FindPasswordActivity.this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(FindPasswordActivity.this, resetCheckInfo.getMsg(),Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        initView();
        btn_send_vercode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account = ed_account.getText().toString();
                if(CheckUtil.checkAccount(FindPasswordActivity.this, account)){
                    APIUtil.invokeResetSendVercodeAPI(send_pcode_handler,
                            account);
                }
            }
        });

        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account = ed_account.getText().toString();
                vercode = ed_vercode.getText().toString();
                password = ed_password.getText().toString();
                if(CheckUtil.checkRegisterInput(FindPasswordActivity.this, account,
                        vercode, password)){
                    APIUtil.invokeRegisterCheckAPI(reset_check_handler,
                            account, vercode, password);
                }
            }
        });
    }

    void initView(){
        ed_account = findViewById(R.id.account_edit_text);
        ed_vercode = findViewById(R.id.vercode_edit_text);
        ed_password = findViewById(R.id.password_edit_text);
        btn_send_vercode = findViewById(R.id.btn_resetpassword_getvercode);
        btn_reset_password = findViewById(R.id.reset_password_btn);

        time = new TimeCount(60000L, 1000L, this.btn_send_vercode);
    }
}
