package com.tuc.search.clause;

import com.tuc.search.core.Clause;
import com.tuc.search.core.ClauseVisitor;

import java.util.ArrayList;
import java.util.List;

public class NestedClause implements Clause {
    private final String path;
    private final List<Clause> innerClauses;

    public NestedClause(String path, List<Clause> innerClauses) {
        this.path = path;
        this.innerClauses = new ArrayList<>(innerClauses);
    }

    @Override
    public String getFieldName() {
        return path;
    }

    @Override
    public Clause.ClauseType getType() {
        return ClauseType.NESTED;
    }

    @Override
    public <T> T accept(ClauseVisitor<T> visitor) {
        return visitor.visitNested(this);
    }

    public String getPath() { return path; }
    public List<Clause> getInnerClauses() { return innerClauses; }
}