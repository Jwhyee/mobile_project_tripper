package com.example.mobile_project_tripper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 전체적인 일기를 추가하면 저장될 table
        String sql = "CREATE TABLE if not exists diary_list ("
                + "d_no integer primary key autoincrement,"
                // 각 다이어리의 고유 번호
                + "d_title text,"
                // 다이어리의 큰 제목
                + "d_start_date date,"
                // 여행 시작 날짜
                + "d_end_date date,"
                // 여행 종료 날짜
                + "d_location text);";
                // 여행지
        db.execSQL(sql);

        // 일기 추가의 + 버튼을 누르면 나오는 데이터를 저장하는 table
        String sql2 = "CREATE TABLE if not exists diary_detail_list ("
                + "d_no integer,"
                // 전체 일기에 참조할 번호
                + "d_detail_sub_title text,"
                // 소제목
                + "d_detail_time text,"
                // 시간
                + "d_detail_transportation text,"
                // 이동수단
                + "d_cost integer);";
                // 비용
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE if exists diary_list";

        db.execSQL(sql);
        onCreate(db);
    }
}