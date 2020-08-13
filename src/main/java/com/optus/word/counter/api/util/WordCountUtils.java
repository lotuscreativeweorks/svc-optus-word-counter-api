package com.optus.word.counter.api.util;

import com.optus.word.counter.api.model.internal.SearchWordCountResult;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WordCountUtils {

    public Map<String, Integer> getTopWordCountsInText(int number, String text) throws IOException {
        Map<String, Integer> map = new HashMap<>();

        for (String word: getWordsFromString(text)) {
            map.put(word, StringUtils.countOccurrencesOf(text, word));
        }

        Map<String,Integer> top =
                map.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(number)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return top;
    }

    public List<SearchWordCountResult> getWordCountsInText(List<String> words, String text) throws IOException {

        List<SearchWordCountResult> searchWordCountResults = new ArrayList<>();
        boolean first = true;

        for (String word: words) {

            int count = StringUtils.countOccurrencesOf(text, word);

            searchWordCountResults.add(new SearchWordCountResult(word, count));
        }
        return searchWordCountResults;
    }

    public Set<String> getWordsFromString(final String str) {
        return new HashSet<>(
                Arrays.asList(str.replaceAll("[^a-zA-Z ]", "").split("\\s+")));
    }
}
