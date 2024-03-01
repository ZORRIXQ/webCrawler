package com.zorrix;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.zorrix.Constants.URLS;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        for(String e : URLS){
            new Thread(() -> {
                try {
                    Crawler crawler = new Crawler(e);
                    crawler.startScrapping();
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }).start();
        }

        System.out.println("Threads are working!");
        
    }

}
