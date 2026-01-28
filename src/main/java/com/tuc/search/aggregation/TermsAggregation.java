package com.tuc.search.aggregation;

public class TermsAggregation implements AggregationSpec {
    private final String field;
    private int size = 10;
    private AggregationOrder order = AggregationOrder.COUNT_DESC;

    public TermsAggregation(String field) {
        this.field = field;
    }

    public TermsAggregation(String field, int size) {
        this.field = field;
        this.size = size;
    }

    @Override
    public String getName() {
        return "terms";
    }

    @Override
    public String getType() {
        return "terms";
    }

    public String getField() { return field; }
    public int getSize() { return size; }
    public AggregationOrder getOrder() { return order; }
}