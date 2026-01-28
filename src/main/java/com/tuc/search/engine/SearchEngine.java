package com.tuc.search.engine;

public enum SearchEngine {
    ELASTICSEARCH("elasticsearch"),
    SOLR("solr"),
    REDIS_SEARCH("redis"),
    RDBMS ("rdbms");

    private final String name;

    SearchEngine(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}