package com.example.mobile_project_tripper;

import android.content.ContentValues;
import android.content.Intent;
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

public class CreateNote extends AppCompatActivity {
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
    TextView titleView, locationView, startDateView, endDateView;
    CheckBox checkBoxTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        String title = getIntent().getStringExtra("title");
        String location = getIntent().getStringExtra("location");
        String startDate = getIntent().getStringExtra("startDate");
        String endDate = getIntent().getStringExtra("endDate");

        titleView = (TextView) findViewById(R.id.title_view);
        locationView = (TextView) findViewById(R.id.location_view);
        startDateView = (TextView) findViewById(R.id.start_date_view);
        endDateView = (TextView) findViewById(R.id.end_date_view);

        titleView.setText(title);
        locationView.setText(location);
        startDateView.setText(startDate);
        endDateView.setText(endDate);


        mDBHelper = new DBHelper(this);
        db = mDBHelper.getWritableDatabase();

        mTitleText = (EditText) findViewById(R.id.txttitle);
        mCost = (EditText) findViewById(R.id.txtcost);
        mDescriptionText = (EditText) findViewById(R.id.description);
        mSpinner = (Spinner) findViewById(R.id.spinnerNoteType);
        pickerDate = (DatePicker) findViewById(R.id.datePicker);
        pickerTime = (TimePicker) findViewById(R.id.timePicker);
        time = (TextView) findViewById(R.id.txtTime);
        date = (TextView) findViewById(R.id.txtDate);
        checkBoxTime = (CheckBox) findViewById(R.id.checkBox);

        pickerDate.setVisibility(View.INVISIBLE);
        pickerTime.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                checkBoxTime.setEnabled(true);
            }

            public void onNothingSelected(AdapterView parent) {
            }
        });

        checkBoxTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    pickerDate.setVisibility(View.VISIBLE);
                    pickerTime.setVisibility(View.VISIBLE);
                    time.setVisibility(View.VISIBLE);
                    date.setVisibility(View.VISIBLE);
                }
                else{
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
        getMenuInflater().inflate(R.menu.menu_create_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_save: // 값들 저장
                String title = mTitleText.getText().toString();
                String cost = mCost.getText().toString();
                String detail = mDescriptionText.getText().toString();
                String type =  mSpinner.getSelectedItem().toString();
                ContentValues cv = new ContentValues();
                cv.put(mDBHelper.TITLE, title);
                cv.put(mDBHelper.COST, cost);
                cv.put(mDBHelper.DETAIL, detail);
                cv.put(mDBHelper.TYPE, type);
                cv.put(mDBHelper.TIME, getString(R.string.Not_Set));

                if (checkBoxTime.isChecked()){ // 날짜 선택 시 날짜까지 저장
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

                db.insert(mDBHelper.TABLE_NAME, null, cv);
                db.insert(mDBHelper.TABLE_NAME_TEMP, null, cv);

                Intent openMainScreen = new Intent(this, WriteView.class);
                Toast.makeText(getApplicationContext(), "일정이 추가됐습니다", Toast.LENGTH_SHORT).show();
                openMainScreen.putExtra("title", titleView.getText().toString());
                openMainScreen.putExtra("location", locationView.getText().toString());
                openMainScreen.putExtra("startDate", startDateView.getText().toString());
                openMainScreen.putExtra("endDate", endDateView.getText().toString());

                openMainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(openMainScreen);
                return true;

            case R.id.action_back:
                Intent openMainActivity = new Intent(this, WriteView.class);

                openMainActivity.putExtra("title", titleView.getText().toString());
                openMainActivity.putExtra("location", locationView.getText().toString());
                openMainActivity.putExtra("startDate", startDateView.getText().toString());
                openMainActivity.putExtra("endDate", endDateView.getText().toString());

                startActivity(openMainActivity);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}