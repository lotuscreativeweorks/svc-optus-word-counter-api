package com.optus.word.counter.api.service;

import com.optus.word.counter.api.model.internal.SearchWordCountResult;
import com.optus.word.counter.api.util.FileUtils;
import com.optus.word.counter.api.util.WordCountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class WordCounterApiService {

    @Autowired
    private WordCountUtils stringUtils;
    @Autowired
    private FileUtils fileUtils;

    public List<SearchWordCountResult> getWordCounts(List<String> words) throws IOException {
        return stringUtils.getWordCountsInText(words, fileUtils.getStringFromTextFile());
    }
    public Map<String,Integer> getTopWordCounts(int number) throws IOException {
        return stringUtils.getTopWordCountsInText(number, fileUtils.getStringFromTextFile());
    }
}
