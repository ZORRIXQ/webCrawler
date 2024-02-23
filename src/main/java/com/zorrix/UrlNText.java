package com.zorrix;

import java.util.Set;

public class UrlNText {
    private String url;
    private Set<String> text;

    public UrlNText(String url, Set<String> text) {
        this.url = url;
        this.text = text;
    }

    public String toString(){
        return "{url = " + this.url + "" +
                "text: " + text + "}";
    }
}
