package com.example.mobile_project_tripper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.IOException;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "diary";
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
    public static final String D_IMG = "d_img";
    public static final String TABLE_NAME_MAIN = "diary_detail";

    public static final String TABLE_NAME_IMG = "images";

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

    private final String createDB_MAIN = "create table if not exists " + TABLE_NAME_MAIN + " ( "
            + D_NO + " integer primary key autoincrement, "
            + D_TITLE + " text, "
            + D_START_DATE + " text, "
            + D_END_DATE + " text, "
            + D_IMG + " blob, "
            + D_LOCATION + " text)";
    private final String carteDB_IMG = "create table if not exists " + TABLE_NAME_IMG + " ("
            + D_IMG + " blob, "
            + D_NO + " integer)";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDB);
        db.execSQL(createDB_MAIN);
        db.execSQL(createDB_temp);
        db.execSQL(carteDB_IMG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
    }

    public Cursor LoadSQLiteDBCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        // Select All Query
        String selectQuery = "SELECT _id,title,cost,type,description,time,date FROM " + TABLE_NAME;
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

    // 메인 엑티비티에서 뿌려지는 DB
    public Cursor LoadSQLiteDBCursor2() {
        SQLiteDatabase db1 = this.getReadableDatabase();
        db1.beginTransaction();
        // Select All Query
        String selectQuery1 = "SELECT d_no,d_title,d_location,d_start_date,d_end_date FROM " + TABLE_NAME_MAIN;
        Cursor cursor1 = null;
        try {
            cursor1 = db1.rawQuery(selectQuery1, null);
            db1.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db1.endTransaction();
        }
        return cursor1;
    }

    public void insert_diary(String D_TITLE, String D_START_DATE, String D_END_DATE, String D_LOCATION){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO diary_detail (D_TITLE, D_START_DATE, D_END_DATE, D_LOCATION) VALUES ('"+ D_TITLE +"',  '"+ D_START_DATE +"' , '"+ D_END_DATE +"', '"+D_LOCATION+"');");
    }

    public void d_insert(String d_title){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM diary_detail_list;");
        db.execSQL("UPDATE images SET d_no = (SELECT d_no FROM diary_detail order by d_no desc limit 1);" );
        db.execSQL("UPDATE diary_detail_list_temp SET d_title = '"+d_title+"' WHERE d_title is NULL;");
        db.execSQL("UPDATE diary_detail SET d_img = (SELECT d_img FROM images) WHERE d_no = (SELECT d_no FROM images) AND d_img IS NULL;");
        db.execSQL("DELETE FROM images");

    }
    //사진 추가
    public Boolean insert_image(String x){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from images;");
        try{
            FileInputStream fs = new FileInputStream(x);
            byte[] imgbyte = new byte[fs.available()];
            fs.read(imgbyte);
            ContentValues contentValues = new ContentValues();
            contentValues.put("d_img", imgbyte);
            db.insert("images", null, contentValues);
            fs.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 사진 뿌리기
    public Bitmap getImage(Long d_no){
        SQLiteDatabase db = getWritableDatabase();
        Bitmap bt = null;
        Cursor cursor = db.rawQuery("select * from diary_detail where d_no = ?", new String[]{String.valueOf(d_no)});
        if(cursor.moveToNext()){
            byte[] img = cursor.getBlob(4 );
            bt = BitmapFactory.decodeByteArray(img, 0, img.length);
        }
        return bt;
    }

}
