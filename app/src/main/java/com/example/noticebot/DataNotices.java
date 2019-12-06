package com.example.noticebot;

public class DataNotices {

    private String title;
    private String link;
    private int id;


    public DataNotices(int id, String title, String link) {
        this.id = id;
        this.title = title;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
