package com.example.mobile_project_tripper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailView extends AppCompatActivity {
    SQLiteDatabase dbs;
    DBHelper dbhelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        final long rid = getIntent().getExtras().getLong(getString(R.string.row_id));
        dbhelp = new DBHelper(this);
        dbs = dbhelp.getWritableDatabase();

        Cursor cursor = dbs.rawQuery("select * from " + dbhelp.TABLE_NAME_MAIN + " where " + dbhelp.D_NO + "=" + rid, null);
        TextView d_title = (TextView) findViewById(R.id.diary_title);
        TextView d_start_day = (TextView) findViewById(R.id.diary_s_day);
        TextView d_end_day = (TextView) findViewById(R.id.diary_e_day);
        TextView d_location = (TextView) findViewById(R.id.diary_location);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                d_title.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbhelp.D_TITLE)));
                d_start_day.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbhelp.D_START_DATE)));
                d_end_day.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbhelp.D_END_DATE)));
                d_location.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbhelp.D_LOCATION)));
            }
            cursor.close();
        }
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, MainActivity.class);
        startActivity(setIntent);
    }

}
