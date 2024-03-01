package com.zorrix;

import com.zorrix.ParsingSiteService.ParsingService;

import java.io.IOException;
import java.util.*;

public class KeyWordsService {
    public List<String> getKeyWords(ArrayList<String> words, String url) throws IOException {
        ParsingService parsingService = new ParsingService(url);
        double percentageToKeyWord = 0.01;
        Map<String, Integer> map = new HashMap<>();
        List<String> result = new ArrayList<>();
        String article = parsingService.parseArticle(url);

        for (String word : words) {
            if (word.length() > 3) {
                map.put(word.toLowerCase(), map.getOrDefault(word.toLowerCase(), 0) + 1);
            }
        }

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
