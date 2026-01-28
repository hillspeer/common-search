package com.tuc.search.core;

public class Pageable {
    private final int page;
    private final int pageSize;

    public Pageable(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() { return page; }
    public int getPageSize() { return pageSize; }
    public int getOffset() { return page * pageSize; }
}