package com.zorrix;

import com.zorrix.ParsingSiteService.ParsingService;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.zorrix.Constants.*;

public class Crawler {
    private Map<Set<String>, String> result;
    private Set<String> urls;
    private ParsingService parsingService;
    Crawler() throws IOException {
        parsingService = new ParsingService(URL);
        result = new ConcurrentHashMap<>();
        urls = new CopyOnWriteArraySet<>(parsingService.parseUrls(URL));
    }


    public  Map<Set<String>, String> startScrapping() throws IOException, URISyntaxException, InterruptedException {
        ParsingService parsingService = new ParsingService(URL);

        writeToFile(urls);

        AtomicInteger input = new AtomicInteger(1);

        ExecutorService executorService = Executors.newFixedThreadPool(100);

        while (urls.size() >= 2 && input.intValue() != 0) {
            executorService.submit(process());
        }

        executorService.shutdown();


        System.out.println(urls.size() + "______________________________");
        return result;
    }

    public Runnable process() {
        return () -> {
            try {
                Iterator<String> urlsIter = urls.iterator();
                String currentUrl = urlsIter.next();
                Set<String> tempUrls = new HashSet<>(parsingService.parseUrls(currentUrl));
                writeToFile(tempUrls);

                result.put(parsingService.parseParagraphs(currentUrl), currentUrl);

                urls.addAll(parsingService.parseUrls(currentUrl));

                writeToFile(currentUrl + ": " + parsingService.getArticle(parsingService.parseArticle(currentUrl)) + "\n");
                System.out.println("parsed url: " + currentUrl + "; Urls size: " + urls.size());

                urls.remove(urls.iterator().next());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public void writeToFile(Set<String> urls){
            try (FileWriter writer = new FileWriter("Urls.txt")) {
                for (String e : urls) {
                    writer.write(e + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    public void writeToFile(String str){
            try (FileWriter writer = new FileWriter("data.txt", true)) {
                writer.write(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


}



/*
Thread mainThread = new Thread(() -> {
            while (urls.size() >= 2 && input.intValue() != 0 && Thread.activeCount() < 100) {
                Thread thread = new Thread(() -> {
                    try {
                        Iterator<String> urlsIter = urls.iterator();
                        String currentUrl = urlsIter.next();
                        Set<String> tempUrls = new HashSet<>(parsingService.parseUrls(currentUrl));
                        writeToFile(tempUrls);

                        result.put(parsingService.parseParagraphs(currentUrl), currentUrl);

                        urls.addAll(parsingService.parseUrls(currentUrl));

                        writeToFile(currentUrl + ": " + parsingService.getArticle(parsingService.parseArticle(currentUrl)) + "\n");
                        System.out.println("parsed url: " + currentUrl + "; Urls size: " + urls.size());

                        urls.remove(urls.iterator().next());
                    } catch (IOException e) {
                        System.out.println(new RuntimeException(e));
                        System.exit(0);
                    }
                });
                thread.start();
                urls.remove(urls.iterator().next());
                System.out.println(thread.getName() + " Started!");
            }


        });
        mainThread.start();
        input.set(scanner.nextInt());
        mainThread.join();
 */





/*
Set<String> previousParagraphs = new HashSet<>();
        previousParagraphs.add("1234567891012134");

        urls.parallelStream().forEach(currentUrl -> {
        try {
Document currentPage = Jsoup.connect(currentUrl).get();
Set<String> currentPagesUrls = parsingService.getUrls(parsingService.parseUrl(currentUrl));
                urls.addAll(currentPagesUrls);

Set<String> paragraphs = parsingService.getParagraphs(parsingService.parseParagraph());
                assert previousParagraphs != null;
        if (!paragraphs.toString().substring(0, 15).contains(previousParagraphs.toString().substring(0, 15)) ){
        result.put(currentUrl, paragraphs);
                    System.out.println(currentUrl + " : " + paragraphs);

                    if (previousParagraphs != null){
        previousParagraphs.clear();
                        previousParagraphs.addAll(paragraphs);
                    }
                            }

                            } catch (IOException e) {
        e.printStackTrace();
            } catch (URISyntaxException e) {
        throw new RuntimeException(e);
            }

                    });
*/