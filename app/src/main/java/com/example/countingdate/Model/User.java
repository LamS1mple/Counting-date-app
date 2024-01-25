package com.example.countingdate.Model;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;

import java.util.Calendar;

public class User {
    private String name;
    private boolean sex;
    private String imgUser;
    private int mask;
    private Calendar date;

    public User() {
    }

    public User(String name, boolean sex, String imgUser, int mask, Calendar date) {
        this.name = name;
        this.sex = sex;
        this.imgUser = imgUser;
        this.mask = mask;
        this.date = date;
    }

    public Uri getUri(){
        Uri uri = Uri.parse(imgUser);
        return uri;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
