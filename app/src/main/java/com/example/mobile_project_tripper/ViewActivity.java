package com.example.mobile_project_tripper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewActivity extends AppCompatActivity {
    PreferenceManager pref;
    TextView view_sub_title;
    TextView view_cost, view_trans;
    Button back_list_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        pref = new PreferenceManager();
        view_sub_title = findViewById(R.id.view_sub_title);
        view_cost = findViewById(R.id.view_cost);
        view_trans = findViewById(R.id.view_trans);
        back_list_btn = findViewById(R.id.back_list_btn);

        // 인텐트로 리사이클러뷰 목록 하나의 키값을 받는다
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");

        String value = pref.getString(getApplication(),key);
        try {
            JSONObject jsonObject = new JSONObject(value);
            String sub_title = (String) jsonObject.getString("sub_title");
            String cost = (String) jsonObject.getString("cost");
            String trans = (String) jsonObject.getString("trans");

            view_sub_title.setText(sub_title);
            view_cost.setText(cost);
            view_trans.setText(trans);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        back_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}