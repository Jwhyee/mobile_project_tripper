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

public class ViewNote extends AppCompatActivity {
    SQLiteDatabase db;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        final long id = getIntent().getExtras().getLong(getString(R.string.row_id));

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + dbHelper.TABLE_NAME_TEMP + " where " + dbHelper.C_ID + "=" + id, null);
        TextView title = findViewById(R.id.title);
        TextView cost = findViewById(R.id.cost);
        TextView detail = findViewById(R.id.detail);
        TextView type = findViewById(R.id.type);
        TextView time = findViewById(R.id.alertvalue);
        TextView date = findViewById(R.id.datevalue);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                title.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.TITLE)));
                cost.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COST)));
                detail.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.DETAIL)));
                type.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.TYPE)));
                time.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.TIME)));
                date.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.DATE)));

            }
            cursor.close();
        }
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, WriteView.class);
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
        switch (item.getItemId()) {
            case R.id.action_back:
                Intent openMainActivity = new Intent(this, WriteView.class);
                startActivity(openMainActivity);
                return true;
            case R.id.action_edit:

                Intent openEditNote = new Intent(ViewNote.this, EditNote.class);
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
                                db.delete(DBHelper.TABLE_NAME_TEMP, DBHelper.C_ID + "=" + id, null);
                                db.delete(DBHelper.TABLE_NAME, DBHelper.C_ID + "=" + id, null);
                                db.close();
                                Intent openMainActivity = new Intent(getApplicationContext(), WriteView.class);
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
