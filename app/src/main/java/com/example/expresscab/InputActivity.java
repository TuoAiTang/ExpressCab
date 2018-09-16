package com.example.expresscab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Entity.AvailableCell;
import com.example.Entity.info.AllocateCellInfo;
import com.example.mytools.APIUtil;
import com.example.mytools.CheckUtil;
import com.example.mytools.GlobalData;
import com.example.mytools.HttpUtil;
import com.example.mytools.JsonParseUtil;
import com.example.mytools.StrUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InputActivity extends AppCompatActivity {

    private String TAG = "投递活动";

    private List<AvailableCell> avail_cells = new ArrayList<>();

    private AllocateCellInfo allocateCellInfo = null;

    private String cab_code;

    private TextView title_cab_code;

    private EditText ed_sendexp_expnum;

    private EditText ed_sendexp_rectel;

    private TextView tv_celltype1;
    private TextView tv_celltype2;
    private TextView tv_celltype3;
    private TextView tv_celltype4;

    private RadioButton rb_celltype1;
    private RadioButton rb_celltype2;
    private RadioButton rb_celltype3;
    private RadioButton rb_celltype4;

    private RadioGroup radioGroup;

    private Button next_step;
    //初始默认0
    private int init_cell_type = 0;

    private Handler cab_info_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            avail_cells = (List<AvailableCell>)msg.obj;
            if(avail_cells.size() == 0){
                Toast.makeText(InputActivity.this, "柜体编号不存在！",
                Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InputActivity.this, InputCabCode.class);
                startActivity(intent);
            }else{
                tv_celltype1.setText("剩余" + avail_cells.get(0).getIdle_count() + "个");
                rb_celltype1.setText(StrUtil.codeToString(avail_cells.get(0).getType()));
                tv_celltype2.setText("剩余" + avail_cells.get(1).getIdle_count() + "个");
                rb_celltype2.setText(StrUtil.codeToString(avail_cells.get(1).getType()));
                tv_celltype3.setText("剩余" + avail_cells.get(2).getIdle_count() + "个");
                rb_celltype3.setText(StrUtil.codeToString(avail_cells.get(2).getType()));
                tv_celltype4.setText("剩余" + avail_cells.get(3).getIdle_count() + "个");
                rb_celltype4.setText(StrUtil.codeToString(avail_cells.get(3).getType()));
                if(avail_cells.get(0).getIdle_count() == 0){
                    rb_celltype1.setClickable(false);
                }
                if(avail_cells.get(1).getIdle_count() == 0){
                    rb_celltype2.setClickable(false);
                }
                if(avail_cells.get(2).getIdle_count() == 0){
                    rb_celltype3.setClickable(false);
                }
                if(avail_cells.get(3).getIdle_count() == 0){
                    rb_celltype4.setClickable(false);
                }
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        initTextView();
                        switch (i){
                            case R.id.rb_celltype1:
                                Toast.makeText(InputActivity.this, "你选择了" + StrUtil.codeToString(avail_cells.get(0).getType()),
                                        Toast.LENGTH_SHORT).show();
                                init_cell_type = avail_cells.get(0).getType();
                                setChange(tv_celltype1);
                                break;
                            case R.id.rb_celltype2:
                                Toast.makeText(InputActivity.this, "你选择了" + StrUtil.codeToString(avail_cells.get(1).getType()),
                                        Toast.LENGTH_SHORT).show();
                                init_cell_type = avail_cells.get(1).getType();
                                setChange(tv_celltype2);
                                break;
                            case R.id.rb_celltype3:
                                Toast.makeText(InputActivity.this, "你选择了" + StrUtil.codeToString(avail_cells.get(2).getType()),
                                        Toast.LENGTH_SHORT).show();
                                init_cell_type = avail_cells.get(2).getType();
                                setChange(tv_celltype3);
                                break;
                            case R.id.rb_celltype4:
                                Toast.makeText(InputActivity.this, "你选择了" + StrUtil.codeToString(avail_cells.get(3).getType()),
                                        Toast.LENGTH_SHORT).show();
                                init_cell_type = avail_cells.get(3).getType();
                                setChange(tv_celltype4);
                                break;
                            default:
                                break;
                        }
                    }
                });

            }
        }
    };

    private Handler alloc_cell_handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            allocateCellInfo = (AllocateCellInfo) msg.obj;
            if(allocateCellInfo.getCode() != 0){
                Toast.makeText(InputActivity.this, allocateCellInfo.getMsg(),
                        Toast.LENGTH_SHORT).show();
                Log.d(TAG, "handleMessage: " + allocateCellInfo.getCode());
                Log.d(TAG, "handleMessage: " + allocateCellInfo.getBody());
                Log.d(TAG, "handleMessage: " + allocateCellInfo.getMsg());
                return;
            }else{
                Toast.makeText(InputActivity.this, "开箱成功！",
                        Toast.LENGTH_SHORT).show();
                GlobalData.setOrder_id(allocateCellInfo.getBody().getOrder_id());
                Intent intent = new Intent(InputActivity.this, InputConfirmActivity.class);
                intent.putExtra("exp_code", ed_sendexp_expnum.getText().toString());
                intent.putExtra("tel", ed_sendexp_rectel.getText().toString());
                intent.putExtra("cab_code", cab_code);
                intent.putExtra("cell_code", allocateCellInfo.getBody().getCode());
                intent.putExtra("order_id", allocateCellInfo.getBody().getOrder_id());
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        cab_code = getIntent().getExtras().getString("cab_code");
        initView();

        next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uid = String.valueOf(GlobalData.getUid());
                String cabinet_code = cab_code;
                int cell_type = init_cell_type;
                String exp_code = ed_sendexp_expnum.getText().toString();
                String consignee_phone = ed_sendexp_rectel.getText().toString();

                if(CheckUtil.checkDelieveryInput(InputActivity.this, cell_type, exp_code, consignee_phone)){
                    APIUtil.invokeAllocateCellAPI(alloc_cell_handler, uid,
                            cabinet_code, cell_type, exp_code,consignee_phone);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        APIUtil.invokeCabinetInfoAPI(cab_info_handler, cab_code);
    }





    void initView(){
        ed_sendexp_expnum = findViewById(R.id.et_sendexp_expnum);
        ed_sendexp_rectel = findViewById(R.id.et_sendexp_rectel);

        tv_celltype1 = findViewById(R.id.tv_celltype1);
        tv_celltype2 = findViewById(R.id.tv_celltype2);
        tv_celltype3 = findViewById(R.id.tv_celltype3);
        tv_celltype4 = findViewById(R.id.tv_celltype4);

        rb_celltype1 = findViewById(R.id.rb_celltype1);
        rb_celltype2 = findViewById(R.id.rb_celltype2);
        rb_celltype3 = findViewById(R.id.rb_celltype3);
        rb_celltype4 = findViewById(R.id.rb_celltype4);

        radioGroup = findViewById(R.id.rg_choose_cell);

        next_step = findViewById(R.id.before_confirm_next);
        title_cab_code = findViewById(R.id.title_cab_code);

        title_cab_code.setText(cab_code);
    }



    void setChange(TextView textView){
        textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        textView.setTextColor(Color.parseColor("#FF7744"));
    }

    void initTextView(){
        tv_celltype1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tv_celltype1.setTextColor(Color.parseColor("#BBBBBB"));
        tv_celltype2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tv_celltype2.setTextColor(Color.parseColor("#BBBBBB"));
        tv_celltype3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tv_celltype3.setTextColor(Color.parseColor("#BBBBBB"));
        tv_celltype4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tv_celltype4.setTextColor(Color.parseColor("#BBBBBB"));
    }
}
