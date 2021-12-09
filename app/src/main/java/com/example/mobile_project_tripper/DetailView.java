package com.example.mobile_project_tripper;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
