package com.tuc.search.translator;

import com.tuc.search.core.ClauseVisitor;
import com.tuc.search.core.SearchQuery;

import java.util.stream.Collectors;

public class SolrTranslator implements ClauseVisitor<String> {

    public String translate(SearchQuery query) {
        // join all top-level clauses with AND by default
        return query.getClauses().stream()
            .map(c -> c.accept(this))
            .collect(Collectors.joining(" AND "));
    }

    @Override
    public String visitTerm(TermClause clause) {
        // name:jhon, age:21, pincode:600021
        return clause.getFieldName() + ":" + escapeValue(clause.getValue());
    }

    @Override
    public String visitPrefix(PrefixClause clause) {
        return "";
    }

    @Override
    public String visitWildcard(WildcardClause clause) {
        return "";
    }

    @Override
    public String visitMatch(MatchClause clause) {
        return "";
    }

    @Override
    public String visitRange(RangeClause clause) {
        return "";
    }

    @Override
    public String visitBoolean(BooleanClause clause) {
        // (MUST...) OR (SHOULD...) etc.
        String mustPart = clause.getMust().stream()
            .map(c -> c.accept(this))
            .collect(Collectors.joining(" AND "));

        String shouldPart = clause.getShould().stream()
            .map(c -> c.accept(this))
            .collect(Collectors.joining(" OR "));

        List<String> parts = new ArrayList<>();
        if (!mustPart.isEmpty())   parts.add(mustPart);
        if (!shouldPart.isEmpty()) parts.add(shouldPart);

        return "(" + String.join(" OR ", parts) + ")";
    }

    @Override
    public String visitNested(NestedClause clause) {
        return "";
    }

    @Override
    public String visitFullText(FullTextClause clause) {
        return "";
    }

    // other visit* methods (match, range, etc.) omitted

    private String escapeValue(Object v) {
        // minimal, extend as needed
        String s = String.valueOf(v);
        return s.contains(" ") ? "\"" + s + "\"" : s;
    }
}
