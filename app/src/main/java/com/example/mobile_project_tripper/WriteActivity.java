package com.example.mobile_project_tripper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {

    Button back_btn;
    Button save_btn;
    EditText sub_title, trans, cost, time;

    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        setInit();
    }

    private void setInit() {
        mDBHelper = new DBHelper(this);

        back_btn = findViewById(R.id.back_btn);
        save_btn = findViewById(R.id.save_btn);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 저장 버튼을 눌러
                // 작성한 editText를 저장

                sub_title = (EditText) findViewById(R.id.sub_title_insert);
                time = (EditText) findViewById(R.id.time_select);
                trans = (EditText) findViewById(R.id.trans_select);
                cost = (EditText) findViewById(R.id.cost_insert);

                mDBHelper.insert_diary(sub_title.getText().toString(), time.getText().toString() ,trans.getText().toString(), cost.getText().toString());

                Intent intent = new Intent(getApplicationContext(), WriteViewActivity.class);
                startActivity(intent);
            }
        });
    }

}