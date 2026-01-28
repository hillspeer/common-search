package com.tuc.search.clause;

import com.tuc.search.core.Clause;
import com.tuc.search.core.ClauseVisitor;

public class TermClause implements Clause {
    private final String fieldName;
    private final Object value;
    private final ClauseContext context;

    public TermClause(String fieldName, Object value) {
        this(fieldName, value, ClauseContext.QUERY);
    }

    public TermClause(String fieldName, Object value, ClauseContext context) {
        this.fieldName = fieldName;
        this.value = value;
        this.context = context;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public ClauseType getType() {
        return ClauseType.TERM;
    }

    @Override
    public <T> T accept(ClauseVisitor<T> visitor) {
        return null;
    }



    public Object getValue() { return value; }
    public ClauseContext getContext() { return context; }
}