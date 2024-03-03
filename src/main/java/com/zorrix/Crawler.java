package com.zorrix;

import com.zorrix.ParsingSiteService.FileWriterService;
import com.zorrix.ParsingSiteService.ParsingService;
import com.zorrix.database.SQLRequestManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.zorrix.Constants.*;
import static com.zorrix.Constants.URL;

public class Crawler {
    private final int numOfThreads = 50;
    private Map<Set<String>, String> result = new ConcurrentHashMap<>();
    private ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
    private Set<String> urls;
    private ParsingService parsingService;
    private KeyWordsService keyWordsService = new KeyWordsService();
    Crawler(String URL) throws IOException {
        parsingService = new ParsingService(URL);
        urls = new CopyOnWriteArraySet<>(parsingService.parseUrls(URL));
    }

    public void startScrapping() throws InterruptedException {
        FileWriterService fileWriter = new FileWriterService();
        fileWriter.writeToFile(urls);

        AtomicInteger input = new AtomicInteger(1);

        //filling urls list with some start urls
        urls.addAll(Arrays.asList(URLS));

        //submitting tasks for executor service, while urls isn't empty
        while (!urls.isEmpty() && input.intValue() != 0) {
            executorService.submit(process());
            Thread.sleep(1);
        }

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.MINUTES);

        System.out.println(urls.size() + "\n" + "______________________________");
    }

    public Runnable process() {
        return () -> {
            try {
                //creating variable program need to work correctly
                FileWriterService fileWriter = new FileWriterService();
                Iterator<String> urlsIter = urls.iterator();
                String currentUrl = urlsIter.next();
                urls.remove(urls.iterator().next());
                Set<String> tempUrls = new HashSet<>(parsingService.parseUrls(currentUrl));
                SQLRequestManager sqlRequestManager = new SQLRequestManager();

                fileWriter.writeToFile(tempUrls);

                for(String url : parsingService.parseUrls(currentUrl)) {
                    if (!urls.contains(currentUrl))
                        urls.add(currentUrl);
                }

                //parsing keywords on current page
                List<String> keyWords = keyWordsService.getKeyWords(currentUrl);
                fileWriter.writeToFile(currentUrl + ": " + String.join(", ",  keyWords) + "\n");
                System.out.println("parsed url: " + currentUrl + "; Urls size: " + urls.size());

                //sending gotten data to DB
                sqlRequestManager.insertData(new SiteId(keyWords, currentUrl));
                System.out.println("Putted data into database");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        };
    }

}
