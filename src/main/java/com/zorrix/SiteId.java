package com.zorrix;

import java.util.ArrayList;
import java.util.List;

//program needs this class for convenient working with keywords and urls
public class SiteId {
    private List<String> keyWords;
    private String url;

    public SiteId(List<String> keyWords, String url){
        this.keyWords = new ArrayList<>(keyWords);
        this.url = url;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public String getUrl() {
        return url;
    }

    public String keyWordsToString(){
        StringBuilder result = new StringBuilder();
        for (String word : keyWords)
            result.append(word).append(", ");

        return result.toString();
    }
}
