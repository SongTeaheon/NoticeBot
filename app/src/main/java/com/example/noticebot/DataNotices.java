package com.example.noticebot;

public class DataNotices {

    private String title;
    private String link;
    private String[] title_N = new String[10];
    private String[] link_N = new String[10];

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
