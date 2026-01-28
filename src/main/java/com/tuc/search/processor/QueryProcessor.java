package com.tuc.search.processor;

import com.tuc.search.core.SearchQuery;
import com.tuc.search.exception.SearchException;
import com.tuc.search.model.SearchResponse;

public interface QueryProcessor {
    SearchResponse execute(SearchQuery query) throws SearchException;
}
