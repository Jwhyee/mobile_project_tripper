package com.example.mobile_project_tripper;

public class DiaryItem_detail {
    private String d_title; // 제목
    private String detail_time; // 세부 시간
    private String detail_subtitle; // 세부 제목
    private String detail_type; // 이동수단
    private String cost;

    public DiaryItem_detail() {
    }

    public String getTitle() {
        return d_title;
    }

    public void setTitle(String d_title) {
        this.d_title = d_title;
    }

    public String getTime() {
        return detail_time;
    }

    public void setTime(String detail_time) {
        this.detail_time = detail_time;
    }

    public String getSubtitle() {
        return detail_subtitle;
    }

    public void setSubtitle(String detail_subtitle) {
        this.detail_subtitle = detail_subtitle;
    }

    public String getType() {
        return detail_type;
    }

    public void setType(String detail_type) {
        this.detail_type = detail_type;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
