package com.example.mobile_project_tripper;

import androidx.appcompat.app.AppCompatActivity;

public class DiaryList extends AppCompatActivity {

    // 이름, 나이, 연락처, 좋아하는 것 멤버변수
    String title;
    String location;
    String date;

    public String getTitle1() {
        return title;
    }

    public void setTitle1(String name) {
        this.title = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
