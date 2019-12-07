package com.witim;

public class ScreenItem {
    String Title, Desc;
    int ScreenImg;

    public ScreenItem(String title, String desc, int screenImg){
        Title = title;
        Desc = desc;
        ScreenImg = screenImg;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public int getScreenImg() {
        return ScreenImg;
    }

    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }
}
