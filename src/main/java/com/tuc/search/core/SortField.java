package com.tuc.search.core;

public class SortField {
    private final String field;
    private final SortOrder order;
    private String unmappedType;

    public SortField(String field, SortOrder order) {
        this.field = field;
        this.order = order;
    }

    public String getField() { return field; }
    public SortOrder getOrder() { return order; }
    public String getUnmappedType() { return unmappedType; }
    public void setUnmappedType(String unmappedType) { this.unmappedType = unmappedType; }
}