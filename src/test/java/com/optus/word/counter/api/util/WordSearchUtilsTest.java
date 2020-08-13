package com.optus.word.counter.api.util;

import com.optus.word.counter.api.model.internal.SearchWordCountResult;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

public class WordSearchUtilsTest {

    WordCountUtils stringUtils = new WordCountUtils();

    @Test
    public void searchWordCountsTest() throws IOException {
        String[] words = {"text1", "text2"};

        List<SearchWordCountResult> response = stringUtils.getWordCountsInText(Arrays.asList(words), "text2, ipsum, et, text1, test, text1, text2");
        assertEquals(response.get(0).getCount(), 2);
        assertEquals(response.get(1).getCount(), 2);
    }

    @Test
    public void searchWordCountsTest2() throws IOException {
        String[] words = {"ipsum"};

        List<SearchWordCountResult> response = stringUtils.getWordCountsInText(Arrays.asList(words), "text2,ipsum,et, ipsum text1, test, text1, text2");
        assertEquals(response.get(0).getCount(), 2);
    }

    @Test
    public void searchWordCountsTest3() throws IOException {
        String[] words = {"ipsum"};

        List<SearchWordCountResult> response = stringUtils.getWordCountsInText(Arrays.asList(words), null);
        assertEquals(response.get(0).getCount(), 0);
    }

    @Test
    public void searchWordCountsTest4() throws IOException {
        String[] words = {null, null};

        List<SearchWordCountResult> response = stringUtils.getWordCountsInText(Arrays.asList(words), null);
        assertEquals(response.get(0).getCount(), 0);
    }

    @Test
    public void getTopWordCountsTest() throws IOException {
        Map<String, Integer> response = stringUtils.getTopWordCountsInText(1, "Vivamus Fred varius Fred posuere ligula. Fred nullam magna metus,");
        assertEquals(response.size(), 1);
        assertEquals(response.get("Fred").intValue(), 3);
    }

    @Test
    public void getTopWordCountsTest1() throws IOException {
        Map<String, Integer> response = stringUtils.getTopWordCountsInText(2, "Fred Vivamus sammy varius Fred sammy posuere ligula. Fred nullam magna metus, Fred");
        assertEquals(response.size(), 2);
        assertEquals(response.get("Fred").intValue(), 4);
        assertEquals(response.get("sammy").intValue(), 2);
    }
}