package com.zorrix.ParsingSiteService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class ParsingService {
    String url;
    public ParsingService(String url){
        this.url = url;
    }

    public Elements parseArticle(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        return document.select("h1");
    }

    public String getArticle(Elements elements){
        return (!elements.isEmpty() ? elements.getLast().text() : "emptyArticle");
    }


    public Set<String> parseParagraphs(String url) throws IOException {
        Set result = new HashSet();

        for (Element e : Jsoup.connect(url).get().select("p"))
            result.add(e.text());

        return result;
    }

    public Set<String> getParagraphs(Elements elements){
        Set result = new HashSet();

        for (Element e : elements)
            result.add(e.text());

        return result;
    }


    public Set<String> parseUrls(String url) throws IOException {
        Set<String> result = new HashSet<>();

        for (Element e : Jsoup.connect(url).get().select("a[href]")){
            if (e.attr("href").length() <= 150 && e.attr("href").startsWith("h") )
                result.add(e.attr("href"));
        }

        return result;
    }


    public Set<String> parseUrls() throws IOException, URISyntaxException {
        Set<String> result = new HashSet<>();
        for (Element e : Jsoup.connect(url).get().select("a[href]")){

            if (e.attr("href").length() <= 150 && e.attr("href").startsWith("h") )
                result.add(e.attr("href"));
        }

        return result;
    }

    public Set<String> getUrls(Elements elements) throws URISyntaxException {
        Set<String> result = new HashSet<>();
        URI uri = new URI(this.url);
        String domain;

        for (Element e : elements){
            domain = uri.getHost();
//            boolean dom = (new URI(e.attr("href")).getHost().equals(domain));

            if (e.attr("href").length() <= 150 && e.attr("href").startsWith("h") )
                result.add(e.attr("href"));
        }

        return result;
    }
}
