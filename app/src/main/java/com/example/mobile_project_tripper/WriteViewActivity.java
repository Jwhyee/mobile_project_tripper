package com.example.mobile_project_tripper;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Iterator;

public class WriteViewActivity extends AppCompatActivity {
    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    private int REQUEST_TEST = 200;
    private int REQUEST_DEL = 201;

    Button write_btn;
    PreferenceManager pref;
    RecyclerView recyclerView;
    MemoAdapter memoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_view);

        this.InitializeView();
        this.InitializeListener();


        pref = new PreferenceManager();
        write_btn = findViewById(R.id.write_btn);
        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivityForResult(intent,REQUEST_TEST);
            }
        });

        //리사이클러뷰 세팅
        LinearLayoutManager linearLayoutManager;
        recyclerView = findViewById(R.id.memo_rv);//리사이클러뷰 findView
        linearLayoutManager = new LinearLayoutManager(WriteViewActivity.this, LinearLayoutManager.VERTICAL, false);

        memoAdapter = new MemoAdapter(WriteViewActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);//linearlayout 세팅
        recyclerView.setAdapter(memoAdapter);//adapter 세팅
    }
    // 날짜지정 시작
    public void InitializeView()
    {
        textView_Date = (TextView)findViewById(R.id.text_view_date);
    }

    public void InitializeListener()
    {
        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                textView_Date.setText(year + "년" + monthOfYear + "월" + dayOfMonth + "일");
            }
        };
    }

    public void OnClickHandler(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 2019, 5, 24);

        dialog.show();
    }

    // 날짜지정 끝

    public void loadMemo() {
        SharedPreferences prefb = getSharedPreferences("memo_contaion", MODE_PRIVATE);
        Collection<?> col_val = prefb.getAll().values();
        Iterator<?> it_val = col_val.iterator();
        Collection<?> col_key = prefb.getAll().keySet();
        Iterator<?> it_key = col_key.iterator();

        while(it_val.hasNext() && it_key.hasNext()){
            String key = (String) it_key.next();
            Log.d("Result", key);
            String value = (String) it_val.next();
            Log.d("Result", value);

            try {
                JSONObject jsonObject = new JSONObject(value);
                String date = (String) jsonObject.getString("date");
                String sub_title = (String) jsonObject.getString("sub_title");
                String cost = (String) jsonObject.getString("cost");
                String trans = (String) jsonObject.getString("trans");
                memoAdapter.addItem(new MemoItem(date,sub_title, cost, trans));
            } catch (JSONException e) {
            }

            memoAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TEST) {
            if (resultCode == RESULT_OK) {

                Intent intent = getIntent();
                String get_date = data.getStringExtra("date");
                String get_sub_title = data.getStringExtra("sub_title");
                String get_trans = data.getStringExtra("trans");
                String get_cost = data.getStringExtra("cost");

                memoAdapter.addItem(new MemoItem(get_date,get_sub_title,get_trans,get_cost));
                memoAdapter.notifyDataSetChanged();
                Toast.makeText(WriteViewActivity.this, "작성 되었습니다", Toast.LENGTH_SHORT).show();

            } else {   // RESULT_CANCEL
                Toast.makeText(WriteViewActivity.this, "저장 되지 않음", Toast.LENGTH_SHORT).show();
            }

        }

        if (requestCode == REQUEST_DEL){
            if(resultCode == RESULT_OK){
                Log.d("Main", "삭제 데이터 옴");
                Intent intent = getIntent();
                String get_key = data.getStringExtra("key");
                Log.d("Main", get_key);
                pref.removeKey(WriteViewActivity.this,get_key);
                memoAdapter.remove(get_key);
                loadMemo();
                memoAdapter.notifyDataSetChanged();
                Toast.makeText(WriteViewActivity.this, "저장 되지 않음", Toast.LENGTH_SHORT).show();
            }
        }
    }
}