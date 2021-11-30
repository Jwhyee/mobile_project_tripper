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

public class ReWriteActivity extends AppCompatActivity {
    PreferenceManager pref;
    Button save_btn;
    EditText title;
    EditText content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Intent get_intent = getIntent();
        String get_key = get_intent.getStringExtra("key");
        String get_title = get_intent.getStringExtra("title");
        String get_content = get_intent.getStringExtra("content");
        Log.d("ReWrite", get_title+"/"+get_content);

        title.setText(get_title);
        content.setText(get_content);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 저장 버튼을 눌러
                // 작성한 editText를 저장
                String edit_title = title.getText().toString();
                String edit_content = content.getText().toString();
                String save_form = "{\"title\":\""+edit_title+"\",\"content\":\""+edit_content+"\"}";

                // key값이 겹치지 않도록 현재 시간으로 부여
                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getTime = simpleDate.format(mDate).toString();

                Log.d("WriteActivity","제목 : "+edit_title+", 내용 : "+edit_content+", 현재시간 : "+get_key);
                pref.setString(getApplication(),get_key,save_form);


                // Intent로 값을 MainActivity에 전달
                Intent intent = new Intent();
                intent.putExtra("date",get_key);
                intent.putExtra("title",edit_title);
                intent.putExtra("content",edit_content);


                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }

}