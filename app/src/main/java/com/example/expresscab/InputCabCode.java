package com.example.expresscab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputCabCode extends AppCompatActivity {

    private EditText cab_code_edit;

    private Button next_step;

    private String TAG = "输入柜体编号";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_cab_code);

        cab_code_edit = findViewById(R.id.cabcode_edit_text);
        next_step = findViewById(R.id.input_cabcode_next);
        next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cab_code = cab_code_edit.getText().toString();
                Log.d(TAG, "cab_code:" + cab_code);
                Intent intent = new Intent(InputCabCode.this, InputActivity.class);
                intent.putExtra("cab_code", cab_code);
                startActivity(intent);
            }
        });

    }
}
