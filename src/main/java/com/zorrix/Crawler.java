package com.zorrix;

import com.zorrix.ParsingSiteService.FileWriterService;
import com.zorrix.ParsingSiteService.ParsingService;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.zorrix.Constants.*;

public class Crawler {
    private Map<Set<String>, String> result;
    private ExecutorService executorService = Executors.newFixedThreadPool(50);
    private Set<String> urls;
    private ParsingService parsingService;
    private KeyWordsService keyWordsService;
    Crawler(String URL) throws IOException {
        parsingService = new ParsingService(URL);
        result = new ConcurrentHashMap<>();
        urls = new CopyOnWriteArraySet<>(parsingService.parseUrls(URL));
        keyWordsService = new KeyWordsService();
    }

    public  Map<Set<String>, String> startScrapping() throws InterruptedException {
        FileWriterService fileWriter = new FileWriterService();
        fileWriter.writeToFile(urls);

        AtomicInteger input = new AtomicInteger(1);

        urls.addAll(Arrays.asList(URLS));

        while (!urls.isEmpty() && input.intValue() != 0) {
            executorService.submit(process());
            Thread.sleep(1);
        }

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.MINUTES);

        System.out.println(urls.size() + "\n" + "______________________________");
        return result;
    }

    public Runnable process() {
        return () -> {
            try {
                FileWriterService fileWriter = new FileWriterService();
                Iterator<String> urlsIter = urls.iterator();
                String currentUrl = urlsIter.next();
                urls.remove(urls.iterator().next());
                Set<String> tempUrls = new HashSet<>(parsingService.parseUrls(currentUrl));

                fileWriter.writeToFile(tempUrls);

                result.put(parsingService.parseParagraphs(currentUrl), currentUrl);

//                urls.addAll(parsingService.parseUrls(currentUrl));

                for(String url : parsingService.parseUrls(currentUrl)) {
                    if (!urls.contains(currentUrl))
                        urls.add(currentUrl);
                }

                List<String> keyWords = keyWordsService.getKeyWords(parsingService.parseAllWords(currentUrl), currentUrl);
//                List<String> keyWords = keyWordsService.getKeyWords(parsingService.parseParagraphs(currentUrl));
                fileWriter.writeToFile(currentUrl + ": " + String.join(", ",  keyWords) + "\n");
                System.out.println("parsed url: " + currentUrl + "; Urls size: " + urls.size());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

}
