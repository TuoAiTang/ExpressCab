package com.example.expresscab;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Entity.info.LoginInfo;
import com.example.Entity.info.RetrieveApplyInfo;
import com.example.Entity.info.RetrieveCheckInfo;
import com.example.mytools.HttpUtil;
import com.example.mytools.JsonParseUtil;
import com.example.mytools.MyToastUtil;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RetrieveActivity extends AppCompatActivity {

    private String uid;

    private String order_id;

    private RetrieveApplyInfo retrieveApplyInfo = null;

    private RetrieveCheckInfo retrieveCheckInfo = null;

    private ImageView iv_suc_fail;

    private Button btn_retrieve_check;

    private TextView tv_retrieve_msg;

    private Handler apply_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            retrieveApplyInfo = (RetrieveApplyInfo)msg.obj;
            //测试方便，==0 --> ！=0
            if(retrieveApplyInfo.getCode() == 0){
                inflateSucView("开箱成功");
            }
            else{
                inflateFailView(retrieveApplyInfo.getMsg());
                Toast toast = MyToastUtil.getCustomToast(RetrieveActivity.this,
                        "开箱失败！\n\n正在返回取件列表...",Toast.LENGTH_SHORT, R.drawable.fail);
                toast.show();
                Timer time = new Timer();
                TimerTask tk = new TimerTask() {
                    Intent intent = new Intent(RetrieveActivity.this, GetExpActivity.class);
                    @Override
                    public void run() {
                        startActivity(intent);
                        finish();
                    }
                };
                time.schedule(tk, 1000);
            }
        }
    };

    private Handler check_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            retrieveCheckInfo = (RetrieveCheckInfo)msg.obj;
            if(retrieveCheckInfo.getCode() == 0){
                Toast toast = MyToastUtil.getCustomToast(RetrieveActivity.this,
                        "取件成功\n\n正在返回取件列表...",Toast.LENGTH_SHORT, R.drawable.success);
                toast.show();
                Timer time = new Timer();
                TimerTask tk = new TimerTask() {
                    Intent intent = new Intent(RetrieveActivity.this, GetExpActivity.class);
                    @Override
                    public void run() {
                        startActivity(intent);
                        finish();
                    }
                };
                time.schedule(tk, 1000);
            }else{
                Toast.makeText(RetrieveActivity.this, retrieveCheckInfo.getMsg(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    private String TAG = "取回快件";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);
        initView();

        Bundle bd = getIntent().getExtras();
        uid = bd.getString("uid");
        order_id = bd.getString("order_id");
        Log.d(TAG, "onCreate: uid:" + uid);
        Log.d(TAG, "onCreate: order_id" + order_id);

        btn_retrieve_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invokeRetriveCheckAPI(uid, order_id);
            }
        });

        invokeRetriveApplyInfo(uid, order_id);
    }

    void invokeRetriveApplyInfo(String uid, String order_id){

        String api_url = "http://101.200.89.170:9002/capp/retrieve/apply";
        RequestBody requestBody = new FormBody.Builder()
                .add("uid", uid)
                .add("order_id", order_id)
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
                retrieveApplyInfo = JsonParseUtil.parseForRetriveApply(responseData);
                Message message = new Message();
                message.obj = retrieveApplyInfo;
                apply_handler.sendMessage(message);
            }
        }, requestBody);
    }
    void invokeRetriveCheckAPI(String uid, String order_id){
        String api_url = "http://101.200.89.170:9002/capp/retrieve/check";
        final RequestBody requestBody = new FormBody.Builder()
                .add("uid", uid)
                .add("order_id", order_id)
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
                RetrieveCheckInfo retrieveCheckInfo = JsonParseUtil
                        .parseForRetrieveChcheckInfo(responseData);
                Message msg = new Message();
                msg.obj = retrieveCheckInfo;
                check_handler.sendMessage(msg);
            }
        }, requestBody);
    }

    void initView(){
        tv_retrieve_msg = findViewById(R.id.tv_retrieve_msg);
        btn_retrieve_check = findViewById(R.id.btn_retrieve_check);
        iv_suc_fail = findViewById(R.id.iv_if_retrieve);
    }


    void inflateFailView(String msg){
        tv_retrieve_msg.setText(msg);
        iv_suc_fail.setImageResource(R.drawable.fail_open);
        btn_retrieve_check.setVisibility(View.GONE);
    }

    void inflateSucView(String msg){
        tv_retrieve_msg.setText(msg);
        iv_suc_fail.setImageResource(R.drawable.suc_open);
        btn_retrieve_check.setVisibility(View.VISIBLE);
    }


}
