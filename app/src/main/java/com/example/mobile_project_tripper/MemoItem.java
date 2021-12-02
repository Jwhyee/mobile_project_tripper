package com.example.mobile_project_tripper;

public class MemoItem {
    String date;
    String sub_title;
    String trans;
    String cost;

    public MemoItem(String date, String sub_title, String trans, String cost) {
        this.date = date;
        this.sub_title = sub_title;
        this.trans = trans;
        this.cost = cost;
    }

    public String get_date() {
        return date;
    }

    public void set_date(String date) { this.date = date; }

    public String get_sub_title() {
        return sub_title;
    }

    public void set_sub_Title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String get_trans() {
        return trans;
    }

    public void set_trans(String trans) {
        this.trans = trans;
    }

    public String get_cost() {
        return cost;
    }

    public void set_cost(String cost) { this.cost = cost; }

}