package com.tuc.search.clause;

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
    public ClauseType getType() {
        return ClauseType.NESTED;
    }

    @Override
    public <T> T accept(ClauseVisitor<T> visitor) {
        return visitor.visitNested(this);
    }

    public String getPath() { return path; }
    public List<Clause> getInnerClauses() { return innerClauses; }
}