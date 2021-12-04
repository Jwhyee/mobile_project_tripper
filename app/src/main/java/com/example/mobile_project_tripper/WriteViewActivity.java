package com.example.mobile_project_tripper;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class WriteViewActivity extends AppCompatActivity {
    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    Button write_btn;
    Context context;

    SQLiteDatabase db;
    DBHelper mDbHelper;

    private RecyclerView listView;
    private ArrayList<MemoItem> memoItemList= new ArrayList<>(); // SQLite에서 가져온 원본 데이터 리스트
    RecyclerView.Adapter listViewAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_view);

        write_btn = findViewById(R.id.write_btn);
        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
            }
        });

        context = this.getBaseContext();

        listView = (RecyclerView)findViewById(R.id.memo_rv);
        listView.setHasFixedSize(true);

        memoItemList.clear(); // 데이터 초기화
        mDbHelper = new DBHelper(this);
        db= mDbHelper.getReadableDatabase();
        db.beginTransaction();

        Cursor cursor = mDbHelper.LoadSQLiteDBCursor();
        try {
            cursor.moveToFirst();
            System.out.println("SQLiteDB 개수 = " + cursor.getCount());
            while (!cursor.isAfterLast()) {
                addGroupItem(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4));
                cursor.moveToNext();
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                db.endTransaction();
            }
        }

        // Set Layout Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);

        listViewAdapter = new MemoAdapter(memoItemList, this); // Adapter 생성
        listView.setAdapter(listViewAdapter); // 어댑터를 리스트뷰에 세팅
    }

    private void addGroupItem(int id, String date, String sub_title, String trans, String cost) {
        MemoItem item = new MemoItem();
        item.setId(id);
        item.setDate(date);
        item.setSub_title(sub_title);
        item.setTrans(trans);
        item.setCost(cost);
        memoItemList.add(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
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

}