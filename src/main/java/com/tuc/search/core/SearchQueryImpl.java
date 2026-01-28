package com.tuc.search.core;

import com.tuc.search.aggregation.AggregationSpec;
import com.tuc.search.engine.SearchEngine;
import com.tuc.search.translator.QueryTranslator;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchQueryImpl implements SearchQuery {
    private  SearchEngine engine = null;
    private  List<Clause> clauses = List.of();
    private  Map<String, AggregationSpec> aggregations = null;
    private  List<SortField> sorts= List.of();
    private  Pageable pageable = null;
    private  Set<String> highlightFields = Set.of();
    private  QueryTranslator translator = null;

    public <T, K, V> SearchQueryImpl(SearchEngine engine, List<T> ts, Map<K,V> kvMap, List<T> ts1, Pageable pageable, Set<T> ts2) {
        this.engine = engine;


    }

    @Override
    public String toQueryString() {
        return "";
    }

    @Override
    public String toJson() {
        return "";
    }

    @Override
    public SearchEngine getEngine() {
        return null;
    }

    @Override
    public List<Clause> getClauses() {
        return List.of();
    }

    @Override
    public Map<String, AggregationSpec> getAggregations() {
        return Map.of();
    }

    @Override
    public List<SortField> getSorts() {
        return List.of();
    }

    @Override
    public Pageable getPageable() {
        return null;
    }

    @Override
    public boolean hasHighlight() {
        return false;
    }

    @Override
    public Set<String> getHighlightFields() {
        return Set.of();
    }

    // Constructor and implementations
}