package com.example.expresscab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class RetrieveActivity extends AppCompatActivity {

    private String uid;

    private String order_id;

    private String TAG = "取回快件";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        Bundle bd = getIntent().getExtras();
        uid = bd.getString("uid");
        order_id = bd.getString("order_id");
        Log.d(TAG, "onCreate: uid:" + uid);
        Log.d(TAG, "onCreate: order_id" + order_id);

    }
}
