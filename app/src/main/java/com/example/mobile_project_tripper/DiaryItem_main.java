package com.example.mobile_project_tripper;

public class DiaryItem_main {
    private Long uid;
    private String title; // 제목
    private String Locate; // 메모 타입
    private String StartDate; // 작성일자
    private String EndDate; // 비용

    public DiaryItem_main() {
    }

    public Long getId() {
        return uid;
    }

    public void setId(Long uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocate() {
        return Locate;
    }

    public void setLocate(String Locate) {
        this.Locate = Locate;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String EndDate) {
        this.EndDate = EndDate;
    }
}
