package com.example.admin.myapplication.model;

/**
 * Created by admin on 2016/03/30.
 */
public class Item {

    private int iId;
    private String iName;

    public Item() {
    }

    public Item(int iId, String iName) {
        this.iId = iId;
        this.iName = iName;
    }

    public int getiId() {
        return iId;
    }

    public String getiName() {
        return iName;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }
}
