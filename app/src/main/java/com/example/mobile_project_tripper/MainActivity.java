package com.example.mobile_project_tripper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<DiaryList> profileDataArray = new ArrayList<>();
    DBHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //DB 추가

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
        helper.onCreate(db);
        RecyclerView rv_profile = findViewById(R.id.diary_list);
        RecyclerView.LayoutManager myLayoutManager = new LinearLayoutManager(this);
        // 레이아웃 매니져를 LinearLayoutManager로 생성

        rv_adapter myAdapter = new rv_adapter(profileDataArray);
        rv_profile.setLayoutManager(myLayoutManager);
        // 생성한 myLayoutManager 객체를 rv_profile 리사이클러뷰의 레이아웃매니져로 설정
        rv_profile.setAdapter(myAdapter);
        // 생성한 myAdapter 객체를 rv_profile의 리사이클러뷰의 어댑터로 설정

        // item(row)추가방법2. 메소드를 통해 객체를 생성하고 profileDataArray에 추가하는 방법 => 해당 객체가 row(item)으로 출력됨
        makeNewDataObject("제목","지역","2021-12-03");

        // 객체가 추가된 ArrayList를 출력하기위해, 리사이클러뷰 어댑터 최신화
        myAdapter.notifyDataSetChanged();

        /*
        //db insert
        String sql = "INSERT INTO mytable('txt') values('Bulgogi');";
        db.execSQL(sql);

        // 리사이클러뷰 db select
        RecyclerView diary_list = (RecyclerView)findViewById(R.id.diary_list);

        String sql2 = "SELECT * FROM mytable;";
        Cursor c = db.rawQuery(sql2,null);
        String[] strs = new String[]{"txt"};
        int[] ints = new int[]{R.id.listview_txt};

        SimpleCursorAdapter adapter = null;
        adapter = new SimpleCursorAdapter(diary_list.getContext(), R.layout.listview, c, strs, ints, 0);
        diary_list.setAdapter(adapter);
         */

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
    public void makeNewDataObject(String title, String location, String date) {
        // ArrayList에 추가할 rv_profile_data 객체를 생성한 뒤,
        DiaryList newDataObject = new DiaryList();

        // 생성자로 받은 parameter들을 rv_profile_data 클래스 set 메소드를 이용하여 이름, 나이, 연락처, 좋아하는 것을 설정해줍니다.
        newDataObject.setTitle1(title);
        newDataObject.setLocation(location);
        newDataObject.setDate(date);

        // 만든 데이터 객체를 ArrayList에 추가합니다. 추가된 데이터는 하나의 item(row)로 출력됩니다.
        profileDataArray.add(newDataObject);
    }
}