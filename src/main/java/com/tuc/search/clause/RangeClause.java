package com.tuc.search.clause;

public class RangeClause implements Clause {
    private final String fieldName;
    private final Object from;
    private final Object to;
    private final RangeOperator operator;
    private final Object value;
    private boolean includeFrom = true;
    private boolean includeTo = true;

    public RangeClause(String fieldName, Object from, Object to) {
        this.fieldName = fieldName;
        this.from = from;
        this.to = to;
        this.operator = null;
        this.value = null;
    }

    public RangeClause(String fieldName, RangeOperator operator, Object value) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
        this.from = null;
        this.to = null;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public ClauseType getType() {
        return ClauseType.RANGE;
    }

    @Override
    public <T> T accept(ClauseVisitor<T> visitor) {
        return visitor.visitRange(this);
    }

    // Getters
    public Object getFrom() { return from; }
    public Object getTo() { return to; }
    public RangeOperator getOperator() { return operator; }
    public Object getValue() { return value; }
    public boolean isIncludeFrom() { return includeFrom; }
    public boolean isIncludeTo() { return includeTo; }
}