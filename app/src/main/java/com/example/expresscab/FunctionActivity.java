package com.example.expresscab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.mytools.GlobalData;

public class FunctionActivity extends AppCompatActivity {

    private ImageButton input;

    private ImageButton output;

    private ImageButton delievery_record;

    private ImageButton cell_info;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        input = findViewById(R.id.btn_choosefunc_input);
        output = findViewById(R.id.btn_choosefunc_output);
        cell_info = findViewById(R.id.btn_choosefunc_cell_info);
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FunctionActivity.this, InputCabCode.class);
                startActivity(intent);
            }
        });
        output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FunctionActivity.this, GetExpActivity.class);
                startActivity(intent);
            }
        });
        cell_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FunctionActivity.this, AllCellActivity.class);
                startActivity(intent);
            }
        });
    }
}
