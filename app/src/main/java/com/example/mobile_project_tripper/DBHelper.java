package com.example.mobile_project_tripper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "diary08";
    public static final String TABLE_NAME = "diary_detail_list";
    public static final String TABLE_NAME_TEMP = "diary_detail_list_temp";
    public static final String C_ID = "_id";
    public static final String COST = "cost";
    public static final String TITLE = "title";
    public static final String TYPE = "type";
    public static final String DETAIL = "description";
    public static final String TIME = "time";
    public static final String DATE = "date";

    public static final int DATABASE_VERSION = 1;
    public static final String D_NO = "d_no";
    public static final String D_TITLE = "d_title";
    public static final String D_START_DATE = "d_start_date";
    public static final String D_END_DATE = "d_end_date";
    public static final String D_LOCATION = "d_location";
    public static final String TABLE_NAME2 = "diary_detail";

    private final String createDB = "create table if not exists " + TABLE_NAME + " ( "
            + C_ID + " integer primary key autoincrement, "
            + D_TITLE + " text, "
            + TITLE + " text, "
            + DETAIL + " text, "
            + TYPE + " text, "
            + TIME + " text, "
            + COST + " text, "
            + DATE + " text)";

    private final String createDB_temp = "create table if not exists " + TABLE_NAME_TEMP + " ( "
            + C_ID + " integer primary key autoincrement, "
            + D_TITLE + " text, "
            + TITLE + " text, "
            + DETAIL + " text, "
            + TYPE + " text, "
            + TIME + " text, "
            + COST + " text, "
            + DATE + " text)";

    private final String createDB2 = "create table if not exists " + TABLE_NAME2 + " ( "
            + D_NO + " integer primary key autoincrement, "
            + D_TITLE + " text, "
            + D_START_DATE + " text, "
            + D_END_DATE + " text, "
            + D_LOCATION + " text)";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDB);
        db.execSQL(createDB2);
        db.execSQL(createDB_temp);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
    }

    public Cursor LoadSQLiteDBCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        // Select All Query
        String selectQuery = "SELECT _id,title,type,description,time,date FROM " + TABLE_NAME;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuery, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return cursor;
    }

    public void insert_diary(String D_TITLE, String D_START_DATE, String D_END_DATE, String D_LOCATION){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO diary_detail (D_TITLE, D_START_DATE, D_END_DATE, D_LOCATION) VALUES ('"+ D_TITLE +"',  '"+ D_START_DATE +"' , '"+ D_END_DATE +"', '"+D_LOCATION+"');");
    }

    public void d_insert(String d_title ){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM diary_detail_list;");
        db.execSQL("UPDATE diary_detail_list_temp SET d_title = '"+d_title+"' WHERE d_title is NULL;");

    }

}
