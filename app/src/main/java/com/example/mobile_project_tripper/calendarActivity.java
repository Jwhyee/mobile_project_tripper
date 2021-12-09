package com.example.mobile_project_tripper;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialCalendar;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.time.DayOfWeek;

public class calendarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        MaterialCalendarView materialCalendarView = (MaterialCalendarView) findViewById(R.id.mtr_c);
        //materialCalendarView.setOnDateChangedListener(this);
        //materialCalendarView.setOnMonthChangedListener(this);
        materialCalendarView.setTopbarVisible(false);

        //int beforeYear = Integer.parseInt(getYear(String.valueOf(beforeMonth().getTime().getYear())));
        //int afterYear = Integer.parseInt(getYear(String.valueOf(afterMonth().getTime().getYear())));

        //materialCalendarView.setSelectedDate(calendarDayToday);
        materialCalendarView.state().edit()
                .isCacheCalendarPositionEnabled(false)
                //.setFirstDayOfWeek(DayOfWeek.SUNDAY)
                //.setMinimumDate(CalendarDay.from(beforeYear, beforeMonth().getTime().getMonth()+1, beforeMonth().getTime().getDate()))
                //.setMaximumDate(CalendarDay.from(afterYear, afterMonth().getTime().getMonth()+1, afterMonth().getTime().getDate()))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);
        materialCalendarView.setDynamicHeightEnabled(true);
        materialCalendarView.setPadding(0, 20, 0, 30);
        //materialCalendarView.setWeekDayTextAppearance(R.style.CustomTextAppearanceWeek);
        //materialCalendarView.setDateTextAppearance(R.style.CustomTextAppearanceDayClicked);
    }
}

