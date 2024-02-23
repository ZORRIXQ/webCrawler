package com.zorrix;

import com.zorrix.ParsingSiteService.ParsingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Constants {
    public static final String URL = "https://kman.kyiv.ua/ua/INFOMATRIX-UKRAINE";
    public static final Document DOCUMENT;

    static {
        try {
            DOCUMENT = Jsoup.connect(Constants.URL)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static final Element sHead = DOCUMENT.head();
    public static final Element sBody = DOCUMENT.body();
    public static final String name = DOCUMENT.title();

    public Constants() throws IOException {
    }
}
