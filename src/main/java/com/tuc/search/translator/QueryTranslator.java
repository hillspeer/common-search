package com.tuc.search.translator;

import com.tuc.search.core.SearchQuery;

public interface QueryTranslator {
    String toQueryString();
    String toJson();
    void setQuery(SearchQuery query);
}