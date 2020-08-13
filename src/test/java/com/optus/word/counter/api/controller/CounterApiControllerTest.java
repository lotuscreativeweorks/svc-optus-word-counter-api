package com.optus.word.counter.api.controller;

import com.optus.word.counter.api.model.SearchRequest;
import com.optus.word.counter.api.model.internal.SearchWordCountResult;
import com.optus.word.counter.api.service.WordCounterApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CounterApiControllerTest {

    @Mock
    private WordCounterApiService service;

    @InjectMocks
    private WordCounterApiController controller;

    @Test
    public void getWordCountsTest() throws IOException {
        List<SearchWordCountResult> results = new ArrayList<>();
        results.add(new SearchWordCountResult("text1", 20));
        results.add(new SearchWordCountResult("text2", 15));
        results.add(new SearchWordCountResult("text3", 10));
        results.add(new SearchWordCountResult("text4", 5));
        SearchRequest searchRequest = new SearchRequest();

        String[] words = {"text1", "text2"};
        searchRequest.setSearchText(Arrays.asList(words));
        when(service.getWordCounts(any())).thenReturn(results);
        String response = controller.getWordCounts(searchRequest);
        assertNotNull(response);
    }

    @Test
    public void getWordCountsServerErrorTest() throws IOException {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            String response = controller.getWordCounts(null);;
        });
        assertEquals(exception.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void getTopWordsTest() throws IOException {
        Map<String, Integer> results = new LinkedHashMap<>();
        results.put("Name1", 10);
        results.put("Name2", 8);
        results.put("Name3", 5);
        results.put("Name4", 3);
        when(service.getTopWordCounts(anyInt())).thenReturn(results);
        String response = controller.getTopWords(5);
        assertNotNull(response);
    }

    @Test
    public void getTopWordsValidationErrorTest() throws IOException {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            String response = controller.getTopWords(-1);
        });
        assertEquals(exception.getStatus(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void getTopWordsValidationErrorTest1() throws IOException {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            String response = controller.getTopWords(3);
        });
        assertEquals(exception.getStatus(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
