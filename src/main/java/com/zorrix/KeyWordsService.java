package com.zorrix;

import com.zorrix.ParsingSiteService.ParsingService;

import java.io.IOException;
import java.util.*;

public class KeyWordsService {
    public List<String> getKeyWords(String url) throws IOException {
        ParsingService parsingService = new ParsingService(url);

        //there must be at least 1% of word for it to be key word
        double percentageToKeyWord = 0.01;
        List<String> words = new ArrayList<>(parsingService.parseAllWords(url));
        Map<String, Integer> map = new HashMap<>();
        List<String> result = new LinkedList<>();
        String article = parsingService.parseArticle(url);

        //counting number of appearances of each word
        for (String word : words) {
            if (word.length() > 3) {
                map.put(word.toLowerCase(), map.getOrDefault(word.toLowerCase(), 0) + 1);
            }
        }

        //if this word we can call as key, adding it to the result LinkedList
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (((double) entry.getValue() / words.size()) >= percentageToKeyWord) {
                result.add(entry.getKey());
            }
        }

        for (String str : article.split(" "))
            if (str.length() > 3)
                result.add(str.toLowerCase());

        return result;
    }


}
