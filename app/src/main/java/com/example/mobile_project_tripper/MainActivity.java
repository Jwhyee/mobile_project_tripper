package com.example.mobile_project_tripper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    DBHelper helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //DB 추가

        helper = new DBHelper(MainActivity.this, "diaryDB.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        //db insert
        String sql = "INSERT INTO mytable('txt') values('Bulgogi');";
        db.execSQL(sql);

        // 리사이클러뷰 db select
        ListView diary_list = (ListView)findViewById(R.id.diary_list);

        String sql2 = "SELECT * FROM mytable;";
        Cursor c = db.rawQuery(sql2,null);
        String[] strs = new String[]{"txt"};
        int[] ints = new int[]{R.id.listview_txt};

        SimpleCursorAdapter adapter = null;
        adapter = new SimpleCursorAdapter(diary_list.getContext(), R.layout.listview, c, strs, ints, 0);
        diary_list.setAdapter(adapter);

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
                Intent intent2 = new Intent(getApplicationContext(), WriteViewActivity.class);
                startActivity(intent2);
            }
        });


    }

}