package com.example.mobile_project_tripper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper helper;
    SQLiteDatabase db;
    private RecyclerView listView_main;
    private ArrayList<DiaryItem_main> diaryItemList_main = new ArrayList<>(); // SQLite에서 가져온 원본 데이터 리스트
    RecyclerView.Adapter listViewAdapter_main; // ListViewAdapter 대신 RecyclerView.Adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //DB 추가

        listView_main = (RecyclerView)findViewById(R.id.diary_list);
        listView_main.setHasFixedSize(true);
        diaryItemList_main.clear(); // 가져온 데이터 초기화
        helper = new DBHelper(this);
        db= helper.getReadableDatabase();
        db.beginTransaction();

        Cursor cursor1 = helper.LoadSQLiteDBCursor2();

        try {
            cursor1.moveToFirst();
            System.out.println("SQLiteDB 개수 = " + cursor1.getCount());
            while (!cursor1.isAfterLast()) {
                addGroupItem(cursor1.getString(0),cursor1.getString(1),
                        cursor1.getString(2),cursor1.getString(3));
                cursor1.moveToNext();
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor1 != null) {
                cursor1.close();
                db.endTransaction();
            }
        }

        // 레이아웃 매니저 세팅
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView_main.setLayoutManager(layoutManager);

        listViewAdapter_main = new ListViewAdapter_main(diaryItemList_main, this); // Adapter 생성
        listView_main.setAdapter(listViewAdapter_main); // 어댑터를 리스트뷰에 세팅

        // 캘린더 이미지 클릭 시 화면 전환
        ImageView calendarImg = (ImageView)findViewById(R.id.calendarImg);
        calendarImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), calendarActivity.class);
                startActivity(intent);
            }
        });

        // 일기 추가 버튼 클릭 시 화면 전환
        Button add_diary_btn = (Button) findViewById(R.id.add_diary_btn);
        add_diary_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), WriteView.class);
                startActivity(intent2);
            }
        });


    }

    public void addGroupItem(String title, String Locate, String sDate, String eDate){
        DiaryItem_main item = new DiaryItem_main();
        item.setTitle(title);
        item.setLocate(Locate);
        item.setStartDate(sDate);
        item.setEndDate(eDate);
        diaryItemList_main.add(item);
    }
}