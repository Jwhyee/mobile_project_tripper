package com.example.mobile_project_tripper;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WriteView extends AppCompatActivity {
    Context context;

    SQLiteDatabase db;
    DBHelper mDBHelper;

    Button save_btn;

    EditText title, location;
    TextView start_date, end_date;
    Uri uri;

    private TextView textView_start_date;
    private TextView textView_end_date;
    private DatePickerDialog.OnDateSetListener callbackMethod_start;
    private DatePickerDialog.OnDateSetListener callbackMethod_end;

    static final int REQUEST_CODE = 1;

    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageview;

    private RecyclerView listView;
    private ArrayList<DiaryItem> diaryItemList = new ArrayList<>(); // SQLite에서 가져온 원본 데이터 리스트
    RecyclerView.Adapter listViewAdapter; // ListViewAdapter 대신 RecyclerView.Adapter
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_view);

        imageview = (ImageView)findViewById(R.id.imageView);
        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        this.InitializeView();
        this.InitializeListener();

        context = this.getBaseContext();

        listView = (RecyclerView)findViewById(R.id.commentslist);
        listView.setHasFixedSize(true);

        diaryItemList.clear(); // 가져온 데이터 초기화
        mDBHelper = new DBHelper(this);
        db= mDBHelper.getReadableDatabase();
        db.beginTransaction();

        Cursor cursor = mDBHelper.LoadSQLiteDBCursor();
        try {
            cursor.moveToFirst();
            System.out.println("SQLiteDB 개수 = " + cursor.getCount());
            while (!cursor.isAfterLast()) {
                addGroupItem(cursor.getLong(0),cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
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

        // 레이아웃 매니저 세팅
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);

        listViewAdapter = new ListViewAdapter(diaryItemList, this); // Adapter 생성
        listView.setAdapter(listViewAdapter); // 어댑터를 리스트뷰에 세팅


        setInit(); // insert_diary 및 save_btn 활성화
    }

    private void setInit() {
        mDBHelper = new DBHelper(this);
        save_btn = findViewById(R.id.save_btn);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = (EditText) findViewById(R.id.title);
                start_date = (TextView) findViewById(R.id.start_date);
                end_date = (TextView) findViewById(R.id.end_date);
                location = (EditText) findViewById(R.id.location);
                imageview = (ImageView)findViewById(R.id.imageView);

                mDBHelper.insert_diary(title.getText().toString(), start_date.getText().toString(), end_date.getText().toString(),location.getText().toString());
                mDBHelper.d_insert(title.getText().toString());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    /*
    public void onClickButton1(View view){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent, REQUEST_CODE);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);
            uri = data.getData();

            ContentValues values = new ContentValues();
            values.put("img", uri.toString());
            db.insert("diary_detail_list",null,values);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);

        }

    }




    public String getPath(Uri uri){
        if(uri == null) return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null );
        if(cursor!= null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();

    }

    public void InitializeView()
    {
        textView_start_date = (TextView)findViewById(R.id.start_date);
        textView_end_date = (TextView)findViewById(R.id.end_date);
    }

    public void InitializeListener()
    {
        callbackMethod_start = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                textView_start_date.setText(year + "년" + monthOfYear + "월" + dayOfMonth + "일");
            }
        };
        callbackMethod_end = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textView_end_date.setText(year + "년" + monthOfYear + "월" + dayOfMonth + "일");
            }
        };
    }

    public void OnClickHandlerStart(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod_start, 2019, 5, 24);

        dialog.show();
    }
    public void OnClickHandlerEnd(View view){
        DatePickerDialog dialog_end = new DatePickerDialog(this, callbackMethod_end, 2019,5,24);
        dialog_end.show();
    }

    public void addGroupItem(Long uid, String title, String cost, String memo_type, String detail, String time, String date){
        DiaryItem item = new DiaryItem();
        item.setUid(uid);
        item.setTitle(title);
        item.setCost(cost);
        item.setMemo_type(memo_type);
        item.setDetail(detail);
        item.setTime(time);
        item.setDate(date);
        diaryItemList.add(item);
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, MainActivity.class);
        startActivity(setIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_new:
                Intent openCreateNote = new Intent(WriteView.this, CreateNote.class);
                startActivity(openCreateNote);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
