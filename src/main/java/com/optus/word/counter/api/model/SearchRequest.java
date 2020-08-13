package com.optus.word.counter.api.model;

import java.util.List;

public class SearchRequest {
    public List<String> searchText;

    public List<String> getSearchText() {
        return searchText;
    }

    public void setSearchText(List<String> searchText) {
        this.searchText = searchText;
    }
}