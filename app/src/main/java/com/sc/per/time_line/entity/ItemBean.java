package com.sc.per.time_line.entity;

public class ItemBean {
    public static final int ITEM_PT = 0;
    public static final int ITEM_GG = 1;

    public ItemBean(int type, String name) {
        this.type = type;
        this.name = name;
    }

    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
