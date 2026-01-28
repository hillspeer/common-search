package com.tuc.search.core;

import com.tuc.search.aggregation.AggregationSpec;
import com.tuc.search.engine.SearchEngine;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SearchQuery {
    String toQueryString();
    String toJson();
    SearchEngine getEngine();
    List<Clause> getClauses();
    Map<String, AggregationSpec> getAggregations();
    List<SortField> getSorts();
    Pageable getPageable();
    boolean hasHighlight();
    Set<String> getHighlightFields();
}