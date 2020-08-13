package com.optus.word.counter.api.controller;


import com.optus.word.counter.api.model.SearchRequest;
import com.optus.word.counter.api.model.internal.SearchWordCountResult;
import com.optus.word.counter.api.service.WordCounterApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequestMapping("counter-api")
@RestController
public class WordCounterApiController {

    public static String SERVER_ERROR_MSG = "Unexpected server error";
    public static String INVALID_NUMBER_MSG = "Invalid number";
    public static List<Integer> VALID_TOP_NUMBERS = Arrays.asList(5, 10, 20, 30);

    @Autowired
    private WordCounterApiService service;

   @RequestMapping(path = "/search",
           method = RequestMethod.POST,
           headers = "Accept=application/json",
           produces = "application/json"
   )
    public String getWordCounts(@RequestBody final SearchRequest request) {
       try {
           return formatSearchWordCountsResponse(service.getWordCounts(request.getSearchText()));
       } catch (Exception e) {
           throw new ResponseStatusException(
                   HttpStatus.INTERNAL_SERVER_ERROR, SERVER_ERROR_MSG, e);
       }
    }

    @RequestMapping(path = "/top/{number}", method = RequestMethod.GET,
            headers = "Accept=text/csv",
            produces = "text/csv")
    public String getTopWords(@PathVariable("number") final int number) throws IOException {

        if (number < 1 || !VALID_TOP_NUMBERS.contains(number)) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY, INVALID_NUMBER_MSG);
        }

       try {
           return formatTopWordCountsResponse(service.getTopWordCounts(number));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, SERVER_ERROR_MSG, e);
        }
    }

    private String formatTopWordCountsResponse(Map<String,Integer> results) {
        StringBuilder response = new StringBuilder();
        for (Map.Entry<String, Integer> item : results.entrySet()) {
            response.append("  ").append(item.getKey()).append("|").append(item.getValue()).append("\n");
        }

        return response.toString();
   }

    private String formatSearchWordCountsResponse (List<SearchWordCountResult> searchWordCountResults) {
        StringBuilder response = new StringBuilder();
        response.append("{\"counts\": [");

       boolean first = true;

        for (SearchWordCountResult searchWordCountResult : searchWordCountResults) {
            if (!first) {
                response.append(", ");
            }
            response.append("{\"").append(searchWordCountResult.getText()).append("\": ").append(searchWordCountResult.getCount()).append("}");
            first = false;
        }
        response.append("]}");
        return response.toString();
    }
}

