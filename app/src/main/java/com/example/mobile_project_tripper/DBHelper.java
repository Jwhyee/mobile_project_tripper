package com.example.mobile_project_tripper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "tripper.db"; // private 거는 이유가 다른 곳에서 사용 될 여지를 만들지 않기 위해서
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
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
                + "d_no integer primary key autoincrement,"
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

    public Cursor LoadSQLiteDBCursor(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        String select_diary = "SELECT * FROM diary_detail_list";
        Cursor cursor = null;
        try{
            cursor = db.rawQuery(select_diary,null);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        return cursor;
    }

    public void insert_diary(String d_detail_sub_title, String d_detail_time, String d_detail_transportation, String d_cost){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO diary_detail_list (d_detail_sub_title, d_detail_time, d_detail_transportation, d_cost) VALUES ('"+ d_detail_sub_title +"',  '"+ d_detail_time +"' , '"+ d_detail_transportation +"', '"+d_cost+"');");
    }

    public void update_diary(String d_detail_sub_title, String d_detail_time, String d_detail_transportation, String d_cost, int d_no){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE diary_detail_list SET d_detail_sub_title = '"+d_detail_sub_title+"', d_detail_time ='"+d_detail_time+"', d_detail_transportation = '"+d_detail_transportation+"', d_cost='"+d_cost+"' WHERE d_no = '"+d_no+"'");
    }

    public void delete_diary(int d_no){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM diary_detail_list WHERE d_no = '"+d_no+"'");

    }

}