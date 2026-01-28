package com.tuc.search.clause;

import com.tuc.search.core.Clause;

public class BooleanQueryBuilder {
    private List<Clause> must = new ArrayList<>();
    private List<Clause> should = new ArrayList<>();
    private List<Clause> filter = new ArrayList<>();
    private List<Clause> mustNot = new ArrayList<>();

    public BooleanQueryBuilder must(Clause clause) {
        if (clause != null) must.add(clause);
        return this;
    }

    public BooleanQueryBuilder should(Clause clause) {
        if (clause != null) should.add(clause);
        return this;
    }

    public BooleanQueryBuilder filter(Clause clause) {
        if (clause != null) filter.add(clause);
        return this;
    }

    public BooleanQueryBuilder mustNot(Clause clause) {
        if (clause != null) mustNot.add(clause);
        return this;
    }

    public BooleanClause build() {
        return new BooleanClause(must, should, filter, mustNot);
    }
}