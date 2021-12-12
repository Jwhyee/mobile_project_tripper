package com.example.mobile_project_tripper;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailView extends AppCompatActivity {
    SQLiteDatabase dbs; // SQLite에 접근할 수 있게 해주는 변수
    DBHelper dbhelp;
    private RecyclerView listView_detail;
    private ArrayList<DiaryItem_detail> diaryItemList_detail = new ArrayList<>(); // SQLite에서 가져온 원본 데이터 리스트
    RecyclerView.Adapter listViewAdapter_detail; // ListViewAdapter 대신 RecyclerView.Adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view); // activity_detail_view에 연동

        final long rid = getIntent().getExtras().getLong(getString(R.string.row_id));
        final String title = getIntent().getStringExtra("d_title");

        dbhelp = new DBHelper(this);
        // DBHelper class에 접근 할 수 있도록 객체 생성
        dbs = dbhelp.getWritableDatabase();
        // 데이터를 읽고 쓰기 위해 DB를 염

        Cursor cursor = dbs.rawQuery("select * from " + dbhelp.TABLE_NAME_MAIN + " where " + dbhelp.D_NO + "=" + rid, null);
        // 해당 SQL을 수행함
        TextView d_title = findViewById(R.id.diary_title);
        TextView d_start_day = findViewById(R.id.diary_s_day);
        TextView d_end_day = findViewById(R.id.diary_e_day);
        TextView d_location = findViewById(R.id.diary_location);
        ImageView diary_img = findViewById(R.id.diary_img);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // 가장 첫 번째 행 선택
                d_title.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbhelp.D_TITLE)));
                d_start_day.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbhelp.D_START_DATE)));
                d_end_day.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbhelp.D_END_DATE)));
                d_location.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbhelp.D_LOCATION)));
                diary_img.setImageBitmap(dbhelp.getImage(rid));
                // 원하는 컬럼값의 인덱스를 받음, 인자값으로 인덱스를 구하고 싶은 컬럼명 작성
            }
            cursor.close();
        }

        //
        listView_detail = (RecyclerView)findViewById(R.id.detail_diary_list);
        listView_detail.setHasFixedSize(true);
        // 가져온 항목의 텍스트 길이가 길어도 recyclerView에 있는 item들의 크기를 고정
        diaryItemList_detail.clear(); // 가져온 데이터 초기화
        dbs.beginTransaction();
        // INSERT, UPDATE, DELETE 등의 작업을 하지 않는 이상 로그가 남지 않음

        Cursor cursor2 = dbhelp.LoadSQLiteDBCursor3(title);

        try {
            cursor2.moveToFirst();
            // 커서를 맨 처음으로 이동시킴
            System.out.println("SQLiteDB 개수 = " + cursor2.getCount());
            while (!cursor2.isAfterLast()) { // 마지막 행 뒤에 있지 않을 때까지 addGroupItem에 해당 인자들을 추가함)
                addGroupItem(cursor2.getString(0),cursor2.getString(1),cursor2.getString(2),
                        cursor2.getString(3),cursor2.getString(4));
                // 해당 SQL에서 가져온 데이터들을 AddGroup에 각 인자들로 보내줌
                cursor2.moveToNext();
                // 맨 처음 -> 다음 -> 다음 ...
            }
            dbs.setTransactionSuccessful();
            // 트랜잭션이 정상적으로 수행됐음을 표시
        } catch (Exception e) {
            e.printStackTrace();
            // 에러 메세지의 발생 근원지를 찾아서 단계별로 에러를 출력
        } finally {
            if (cursor2 != null) {
                cursor2.close();
                dbs.endTransaction();
            }
        }

        // 레이아웃 매니저 세팅
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 리니어레이아웃을 VERTICAL으로 지정
        listView_detail.setLayoutManager(layoutManager);

        listViewAdapter_detail = new ListViewAdapter_detail(diaryItemList_detail, this); // Adapter 생성
        listView_detail.setAdapter(listViewAdapter_detail); // 어댑터를 리스트뷰에 세팅
        //
    }

    public void addGroupItem(String d_title, String subtitle, String type, String time, String cost){
        DiaryItem_detail item = new DiaryItem_detail();
        item.setTitle(d_title);
        item.setSubtitle(subtitle);
        item.setType(type);
        item.setTime(time);
        item.setCost(cost);

        diaryItemList_detail.add(item);
        // 리사이클러 뷰에 해당 아이템들을 추가함
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, MainActivity.class);
        startActivity(setIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final long id = getIntent().getExtras().getLong(getString(R.string.rowID));
        final String title = getIntent().getStringExtra("d_title");
        switch (item.getItemId()) {
            case R.id.action_back:
                Intent openMainActivity = new Intent(this, MainActivity.class);
                startActivity(openMainActivity);
                return true;

            case R.id.action_edit:

                Intent openEditNote = new Intent(DetailView.this, EditNote.class);
                openEditNote.putExtra(getString(R.string.intent_row_id), id);
                startActivity(openEditNote);
                return true;

            case R.id.action_discard:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder
                        .setTitle(getString(R.string.delete_title))
                        .setMessage(getString(R.string.delete_message))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dbs.delete(DBHelper.TABLE_NAME_MAIN, DBHelper.D_NO + "=" + id, null);
                                dbs.delete(DBHelper.TABLE_NAME_TEMP, DBHelper.D_TITLE + "=" + title, null);
                                dbs.close();
                                Intent openMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                                Toast.makeText(getApplicationContext(), "삭제 됐습니다", Toast.LENGTH_SHORT).show();
                                startActivity(openMainActivity);

                            }
                        })
                        .setNegativeButton(getString(R.string.no), null)    //Do nothing on no
                        .show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}