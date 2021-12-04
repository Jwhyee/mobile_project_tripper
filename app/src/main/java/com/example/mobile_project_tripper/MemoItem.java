package com.example.mobile_project_tripper;

public class MemoItem {
    private Long uid;
    private String title; // 제목
    private String memo_type; // 메모 타입
    private String detail; // 내용
    private String time; // 시간
    private String date; // 작성일자
    private String cost; // 비용

    public MemoItem() {

    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public MemoItem(Long uid, String title, String memo_type, String detail, String time, String date, String cost) {
        this.uid = uid;
        this.title = title;
        this.memo_type = memo_type;
        this.detail = detail;
        this.time = time;
        this.date = date;
        this.cost = cost;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo_type() {
        return memo_type;
    }

    public void setMemo_type(String memo_type) {
        this.memo_type = memo_type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
