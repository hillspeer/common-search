package com.tuc.search.clause;

import com.tuc.search.core.Clause;
import com.tuc.search.core.ClauseVisitor;

public class MatchClause implements Clause {
    private final String fieldName;
    private final String value;
    private final boolean phrase;
    private float boost = 1.0f;
    private int fuzziness = 0;
    private Operator operator = Operator.OR;

    public MatchClause(String fieldName, String value) {
        this(fieldName, value, false);
    }

    public MatchClause(String fieldName, String value, boolean phrase) {
        this.fieldName = fieldName;
        this.value = value;
        this.phrase = phrase;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public ClauseType getType() {
        return ClauseType.MATCH;
    }

    @Override
    public <T> T accept(ClauseVisitor<T> visitor) {
        return visitor.visitMatch(this);
    }

    // Getters and setters
    public String getValue() { return value; }
    public boolean isPhrase() { return phrase; }
    public float getBoost() { return boost; }
    public void setBoost(float boost) { this.boost = boost; }
    public int getFuzziness() { return fuzziness; }
    public void setFuzziness(int fuzziness) { this.fuzziness = fuzziness; }
    public Operator getOperator() { return operator; }
    public void setOperator(Operator operator) { this.operator = operator; }

    public enum Operator {
        AND, OR
    }
}