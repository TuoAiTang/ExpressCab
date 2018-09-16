package com.example.expresscab;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Entity.info.DeliveryConfirmInfo;
import com.example.mytools.APIUtil;
import com.example.mytools.GlobalData;
import com.example.mytools.HttpUtil;
import com.example.mytools.JsonParseUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InputConfirmActivity extends AppCompatActivity {

    private String exp_code;
    private String exp_rectel;
    private String cab_code;
    private String cell_code;
    private String order_id;

    private TextView tv_exp_code;
    private TextView tv_exp_rectel;
    private TextView tv_cab_code;
    private TextView tv_cell_code;
    private Button dismiss_btn;
    private Button complete_btn;

    private DeliveryConfirmInfo deliveryConfirmInfo;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            deliveryConfirmInfo = (DeliveryConfirmInfo)msg.obj;
            if(deliveryConfirmInfo.getCode() == 0){
//                Toast.makeText(InputConfirmActivity.this, "投递成功",
//                        Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialog = new AlertDialog.Builder(InputConfirmActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("投递完成");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        InputConfirmActivity.this.finish();
                    }
                });
                dialog.show();
            }else{
                Toast.makeText(InputConfirmActivity.this, deliveryConfirmInfo.getMsg(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_confirm);

        //获取上一个活动传递的参数以初始化界面
        Bundle bd = getIntent().getExtras();
        exp_code   = bd.getString("exp_code");
        exp_rectel = bd.getString("tel");
        cab_code   = bd.getString("cab_code");
        cell_code  = bd.getString("cell_code");
        order_id = bd.getString("order_id");

        //初始化
        initView();

        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIUtil.invokeDeliveryConfirmAPI(handler, GlobalData.getUid(), order_id);
            }
        });

        dismiss_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(InputConfirmActivity.this, "取消成功",
                        Toast.LENGTH_SHORT).show();
                InputConfirmActivity.this.finish();
            }
        });
    }

    void initView(){
        tv_exp_code = findViewById(R.id.tv_exp_code);
        tv_exp_rectel = findViewById(R.id.tv_exp_rectel);
        tv_cab_code = findViewById(R.id.tv_cab_code);
        tv_cell_code = findViewById(R.id.tv_cell_code);
        dismiss_btn = findViewById(R.id.dismiss_delivery);
        complete_btn = findViewById(R.id.complete_delivery);

        tv_exp_code.setText(exp_code);
        tv_exp_rectel.setText(exp_rectel);
        tv_cab_code.setText(cab_code);
        tv_cell_code.setText(cell_code);
    }
}
