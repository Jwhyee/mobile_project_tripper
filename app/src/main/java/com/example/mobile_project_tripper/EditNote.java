package com.example.mobile_project_tripper;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditNote extends AppCompatActivity {
    SQLiteDatabase db;
    DBHelper mDBHelper;
    EditText mTitleText;
    EditText mDescriptionText;
    EditText mCost;
    Spinner mSpinner;
    DatePicker pickerDate;
    TimePicker pickerTime;
    TextView time;
    TextView date;
    CheckBox checkBoxAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        mDBHelper = new DBHelper(this);
        db = mDBHelper.getWritableDatabase();

        mTitleText = (EditText) findViewById(R.id.txttitle);
        mCost = (EditText) findViewById(R.id.txtcost);
        mDescriptionText = (EditText) findViewById(R.id.description);
        mSpinner = (Spinner) findViewById(R.id.spinnerNoteType);
        pickerDate = (DatePicker) findViewById(R.id.datePicker2);
        pickerTime = (TimePicker) findViewById(R.id.timePicker2);
        time = (TextView) findViewById(R.id.txt_selecttime);
        date = (TextView) findViewById(R.id.txt_selectdate);
        checkBoxAlarm = (CheckBox) findViewById(R.id.chkbox);

        final long id = getIntent().getExtras().getLong(getString(R.string.row_id_log));

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.note_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView parent, View view, int position, long id) {
                        checkBoxAlarm.setEnabled(true);
                    }

                    public void onNothingSelected(AdapterView parent) {

                    }
                });

        Cursor cursor = db.rawQuery("select * from " + mDBHelper.TABLE_NAME_TEMP + " where " + mDBHelper.C_ID + "=" + id, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                mTitleText.setText(cursor.getString(cursor.getColumnIndexOrThrow(mDBHelper.TITLE)));
                mCost.setText(cursor.getString(cursor.getColumnIndexOrThrow(mDBHelper.COST)));
                mDescriptionText.setText(cursor.getString(cursor.getColumnIndexOrThrow(mDBHelper.DETAIL)));
                if (cursor.getString(cursor.getColumnIndexOrThrow(mDBHelper.TYPE)).equals(mSpinner.getItemAtPosition(0))) {
                    mSpinner.setSelection(0);
                    checkBoxAlarm.setChecked(false);
                    checkBoxAlarm.setEnabled(false);
                    pickerDate.setVisibility(View.INVISIBLE);
                    pickerTime.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.INVISIBLE);
                    date.setVisibility(View.INVISIBLE);
                } else if (cursor.getString(cursor.getColumnIndexOrThrow(mDBHelper.TYPE)).equals(mSpinner.getItemAtPosition(1))) {
                    mSpinner.setSelection(1);
                    checkBoxAlarm.setChecked(false);
                    checkBoxAlarm.setEnabled(false);
                    pickerDate.setVisibility(View.INVISIBLE);
                    pickerTime.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.INVISIBLE);
                    date.setVisibility(View.INVISIBLE);
                } else if (cursor.getString(cursor.getColumnIndexOrThrow(mDBHelper.TYPE)).equals(mSpinner.getItemAtPosition(2))) {
                    mSpinner.setSelection(2);
                    checkBoxAlarm.setChecked(true);
                    checkBoxAlarm.setEnabled(true);

                }
                if (cursor.getString(cursor.getColumnIndexOrThrow(mDBHelper.TIME)).toString().equals(getString(R.string.Not_Set_Alert))) {
                    checkBoxAlarm.setChecked(false);
                    pickerDate.setVisibility(View.INVISIBLE);
                    pickerTime.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.INVISIBLE);
                    date.setVisibility(View.INVISIBLE);

                }


            }
            cursor.close();
        }
        checkBoxAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    pickerDate.setVisibility(View.VISIBLE);
                    pickerTime.setVisibility(View.VISIBLE);
                    time.setVisibility(View.VISIBLE);
                    date.setVisibility(View.VISIBLE);
                } else {
                    pickerDate.setVisibility(View.INVISIBLE);
                    pickerTime.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.INVISIBLE);
                    date.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, WriteView.class);
        startActivity(setIntent);
    }

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_back:
                Intent openMainActivity = new Intent(this, WriteView.class);
                startActivity(openMainActivity);
                return true;
            case R.id.action_save:
                final long id = getIntent().getExtras().getLong(getString(R.string.row_id_long));
                String title = mTitleText.getText().toString();
                String cost = mCost.getText().toString();
                String detail = mDescriptionText.getText().toString();
                String type = mSpinner.getSelectedItem().toString();
                ContentValues cv = new ContentValues();
                cv.put(mDBHelper.TITLE, title);
                cv.put(mDBHelper.COST, cost);
                cv.put(mDBHelper.DETAIL, detail);
                cv.put(mDBHelper.TYPE, type);
                cv.put(mDBHelper.TIME, getString(R.string.Not_Set));
                cv.putNull(mDBHelper.DATE);

                if (checkBoxAlarm.isChecked()){
                    Calendar calender = Calendar.getInstance();
                    calender.clear();
                    calender.set(Calendar.MONTH, pickerDate.getMonth());
                    calender.set(Calendar.DAY_OF_MONTH, pickerDate.getDayOfMonth());
                    calender.set(Calendar.YEAR, pickerDate.getYear());
                    calender.set(Calendar.HOUR, pickerTime.getCurrentHour());
                    calender.set(Calendar.MINUTE, pickerTime.getCurrentMinute());
                    calender.set(Calendar.SECOND, 00);

                    SimpleDateFormat formatter = new SimpleDateFormat(getString(R.string.hour_minutes));
                    String timeString = formatter.format(new Date(calender.getTimeInMillis()));
                    SimpleDateFormat dateformatter = new SimpleDateFormat(getString(R.string.dateformate));
                    String dateString = dateformatter.format(new Date(calender.getTimeInMillis()));

                    cv.put(mDBHelper.TIME, timeString);
                    cv.put(mDBHelper.DATE, dateString);

                }

                db.update(mDBHelper.TABLE_NAME_TEMP, cv, mDBHelper.C_ID + "=" + id, null);
                db.update(mDBHelper.TABLE_NAME, cv, mDBHelper.C_ID + "=" + id, null);

                Intent openMainScreen = new Intent(EditNote.this, WriteView.class);
                openMainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(openMainScreen);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
