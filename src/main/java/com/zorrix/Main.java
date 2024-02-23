package com.zorrix;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        Crawler crawler = new Crawler();
        crawler.startScrapping();

//        for (Map.Entry<String, Set<String>> e : crawler.startScrapping().entrySet())
//            System.out.println(e.getKey() + " : " + e.getValue());
    }

}
