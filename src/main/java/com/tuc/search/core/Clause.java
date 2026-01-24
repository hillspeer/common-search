package com.tuc.search.framework.core;

public interface Clause {
    String getFieldName();
    ClauseType getType();
    <T> T accept(ClauseVisitor<T> visitor);

    enum ClauseType {
        MATCH, RANGE, BOOLEAN, NESTED, FULL_TEXT, TERM, PREFIX, WILDCARD
    }
}