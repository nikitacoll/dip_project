package com.example.dip_project.Class;

public class DictionaryThem {
    private String URL;
    private String text;

    public DictionaryThem(String URL, String text) {
        this.URL = URL;
        this.text = text;
    }

    public String getURL() {
        return URL;
    }

    public String getText() {
        return text;
    }
}
