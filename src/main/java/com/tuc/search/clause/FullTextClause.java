package com.tuc.search.clause;

public class FullTextClause implements Clause {
    private final String fieldName;
    private final String query;
    private float phraseBoost = 1.5f;
    private int slop = 0;

    public FullTextClause(String fieldName, String query) {
        this.fieldName = fieldName;
        this.query = query;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public ClauseType getType() {
        return ClauseType.FULL_TEXT;
    }

    @Override
    public <T> T accept(ClauseVisitor<T> visitor) {
        return visitor.visitFullText(this);
    }

    public String getQuery() { return query; }
    public float getPhraseBoost() { return phraseBoost; }
    public int getSlop() { return slop; }
}