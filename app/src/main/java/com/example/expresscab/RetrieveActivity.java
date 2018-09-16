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
import com.example.Entity.info.RegiResetCheckInfo;
import com.example.Entity.info.RetrieveApplyInfo;
import com.example.Entity.info.RetrieveCheckInfo;
import com.example.mytools.APIUtil;
import com.example.mytools.ActivityUtil;
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
            if(retrieveApplyInfo.getCode() != 0){
                inflateSucView(retrieveApplyInfo.getMsg());
            }
            else{
                inflateFailView(retrieveApplyInfo.getMsg());
                MyToastUtil.getCustomToast(RetrieveActivity.this,
                        "开箱失败！\n\n正在返回取件列表...",
                        Toast.LENGTH_SHORT, R.drawable.fail).show();
                ActivityUtil.delayJump(RetrieveActivity.this,
                        GetExpActivity.class, 1000);

            }
        }
    };

    private Handler check_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            retrieveCheckInfo = (RetrieveCheckInfo)msg.obj;
            if(retrieveCheckInfo.getCode() == 0){
                Log.d(TAG, "handleMessage: body:" + retrieveCheckInfo.getBody());
                Log.d(TAG, "handleMessage: msg:" + retrieveCheckInfo.getMsg());
                MyToastUtil.getCustomToast(RetrieveActivity.this,
                        "取件成功\n\n正在返回取件列表...",Toast.LENGTH_SHORT,
                        R.drawable.success).show();
                ActivityUtil.delayJump(RetrieveActivity.this,
                        GetExpActivity.class, 1000);
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
        //为隐藏的按钮设置点击事件
        btn_retrieve_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            APIUtil.invokeRetriveCheckAPI(check_handler, uid, order_id);
            }
        });
        //创建时就调用接口发出开箱申请
        APIUtil.invokeRetriveApplyInfo(apply_handler, uid, order_id);
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
