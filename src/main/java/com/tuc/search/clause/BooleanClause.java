package com.tuc.search.clause;

import com.tuc.search.core.Clause;
import com.tuc.search.core.ClauseVisitor;

import java.util.ArrayList;
import java.util.List;

public class BooleanClause implements Clause {
    private final List<Clause> must;
    private final List<Clause> should;
    private final List<Clause> filter;
    private final List<Clause> mustNot;
    private int minimumShouldMatch = 1;

    public BooleanClause(List<Clause> must, List<Clause> should, 
                         List<Clause> filter, List<Clause> mustNot) {
        this.must = new ArrayList<>(must);
        this.should = new ArrayList<>(should);
        this.filter = new ArrayList<>(filter);
        this.mustNot = new ArrayList<>(mustNot);
    }

    @Override
    public String getFieldName() {
        return "bool";
    }

    @Override
    public ClauseType getType() {
        return ClauseType.BOOLEAN;
    }

    @Override
    public <T> T accept(ClauseVisitor<T> visitor) {
        return null;
    }


    public List<Clause> getMust() { return must; }
    public List<Clause> getShould() { return should; }
    public List<Clause> getFilter() { return filter; }
    public List<Clause> getMustNot() { return mustNot; }
}