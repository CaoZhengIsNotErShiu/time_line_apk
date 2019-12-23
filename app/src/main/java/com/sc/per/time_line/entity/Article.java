package com.sc.per.time_line.entity;

import android.content.Intent;

public class Article {
    private Intent id;
    private String userName;
    private int image;
    private String title;
    private String data;

    public Article() {

    }

    public Article(String userName, int image) {
        this.userName = userName;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Intent getId() {
        return id;
    }

    public void setId(Intent id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
