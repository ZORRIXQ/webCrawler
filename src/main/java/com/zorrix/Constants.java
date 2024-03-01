package com.zorrix;

import com.zorrix.ParsingSiteService.ParsingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Constants {
    public static final String URL = "https://github.com";

    public static final String[] URLS = {"https://github.com",
            "https://www.instagram.com" ,
            "https://javarush.com/groups/posts/2370-pattern-proektirovanija-factory",
            "https://www.shopify.com/ppc/website/website-builder?term=custom%20website%20builder&adid=676798346843&campaignid=15439902707&utm_medium=cpc&utm_source=google&bucket=website_other&gad_source=1&gclid=Cj0KCQiAoeGuBhCBARIsAGfKY7wp38ZdEngSy_fGsQJAK2G8h2N8BTTkhvw5qkUS8iy4de71EJgEFlEaArQmEALw_wcB&cmadid=516586848;cmadvertiserid=10730501;cmcampaignid=26990768;cmplacementid=324286430;cmcreativeid=163722649;cmsiteid=5500011",
            "https://www.virustotal.com/",
            "https://www.postgresql.org",
            "https://uk.wikipedia.org/wiki/"
    };

    public static final Document DOCUMENT;
    public static final String EMPTY_ARTICLE = "emptyArticle";

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
}
