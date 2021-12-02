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

    PreferenceManager pref;
    Button back_btn;
    Button save_btn;
    EditText sub_title, trans, cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);


        pref = new PreferenceManager();
        back_btn = findViewById(R.id.back_btn);
        save_btn = findViewById(R.id.save_btn);
        // editText 할당
        sub_title = findViewById(R.id.sub_title_insert);
        trans = findViewById(R.id.trans_select);
        cost = findViewById(R.id.cost_insert);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 저장 버튼을 눌러
                // 작성한 editText를 저장
                String edit_sub_title = sub_title.getText().toString();
                String edit_trans = trans.getText().toString();
                String edit_cost = cost.getText().toString();
                // String 값을 JSONObject로 변환하여 사용할 수 있도록 메모의 제목과 타이틀을 JSON 형식로 저장
                String save_form = "{\"sub_title\":\""+edit_sub_title+"\",\"trans\":\""+edit_trans+"\",\"cost\":\""+edit_cost+"\"}";

                // key값이 겹치지 않도록 현재 시간으로 부여
                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getTime = simpleDate.format(mDate).toString();

                Log.d("WriteActivity","부제목 : "+edit_sub_title+", 이동수단 : "+edit_trans+", 현재시간 : "+getTime+", 비용 : "+edit_cost);
                //PreferenceManager 클래스에서 저장에 관한 메소드를 관리
                pref.setString(getApplication(),getTime,save_form);


                // Intent로 값을 MainActivity에 전달
                Intent intent = new Intent();
                intent.putExtra("date",getTime);
                intent.putExtra("sub_title",edit_sub_title);
                intent.putExtra("trans",edit_trans);
                intent.putExtra("content",edit_cost);
                setResult(RESULT_OK, intent);
                finish();

            }
        });


    }

}