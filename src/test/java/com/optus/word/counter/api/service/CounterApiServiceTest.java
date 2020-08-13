
package com.optus.word.counter.api.service;

import com.optus.word.counter.api.model.internal.SearchWordCountResult;
import com.optus.word.counter.api.util.FileUtils;
import com.optus.word.counter.api.util.WordCountUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CounterApiServiceTest {

    @Mock
    private WordCountUtils stringUtils;
    @Mock
    private FileUtils fileUtils;


    @InjectMocks
    private WordCounterApiService service;

    @Test
    public void searchWordCounts() throws IOException {
        List<SearchWordCountResult> results = new ArrayList<>();
        results.add(new SearchWordCountResult("text1", 20));
        results.add(new SearchWordCountResult("text2", 15));
        results.add(new SearchWordCountResult("text3", 10));
        results.add(new SearchWordCountResult("text4", 5));

        String[] words = {"text1", "text2"};
        when(stringUtils.getWordCountsInText(any(), any())).thenReturn(results);
        when(fileUtils.getStringFromTextFile()).thenReturn("");
        List<SearchWordCountResult> response = service.getWordCounts(Arrays.asList(words));
        assertNotNull(response);
    }

    @Test
    public void getTopWordCountsTest() throws IOException {
        Map<String, Integer> results = new LinkedHashMap<>();
        results.put("Name1", 10);
        results.put("Name2", 8);
        results.put("Name3", 5);
        results.put("Name4", 3);
        when(stringUtils.getTopWordCountsInText(anyInt(), any())).thenReturn(results);
        when(fileUtils.getStringFromTextFile()).thenReturn("");
        Map<String, Integer> response = service.getTopWordCounts(3);
        assertNotNull(response);
    }
}