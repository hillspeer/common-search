package com.tuc.search.core;

import com.tuc.search.clause.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SolrClauseVisitor implements ClauseVisitor<String> {

    @Override
    public String visitTerm(TermClause c) {
        // field:value
        return c.getFieldName() + ":" + escape(c.getValue());
    }

    @Override
    public String visitMatch(MatchClause c) {
        String field = c.getFieldName();
        String v = escape(c.getValue());
        String expr = c.isPhrase()
            ? field + ":\"" + v + "\""                     // phrase
            : field + ":" + v;                             // simple term

        if (c.getBoost() != 1.0f) {
            expr += "^" + c.getBoost();                    // boosting in Solr
        }
        return expr;
    }

    @Override
    public String visitRange(RangeClause clause) {
        return "";
    }


    @Override
    public String visitNested(NestedClause clause) {
        return "";
    }



    @Override
    public String visitBoolean(BooleanClause b) {
        List<String> parts = new ArrayList<>();

        if (!b.getMust().isEmpty()) {
            String must = b.getMust().stream()
                .map(cl -> cl.accept(this))
                .collect(Collectors.joining(" AND "));
            parts.add("(" + must + ")");
        }

        if (!b.getShould().isEmpty()) {
            String should = b.getShould().stream()
                .map(cl -> cl.accept(this))
                .collect(Collectors.joining(" OR "));
            parts.add("(" + should + ")");
        }

        if (!b.getMustNot().isEmpty()) {
            String not = b.getMustNot().stream()
                .map(cl -> "-" + cl.accept(this))          // -clause = NOT
                .collect(Collectors.joining(" "));
            parts.add(not);
        }

        if (parts.isEmpty()) return "*:*";                 // match all
        return "(" + String.join(" AND ", parts) + ")";
    }

    // nested/fulltext/prefix/wildcard similar, using Solr syntax…

    private String escape(Object v) {
        String s = String.valueOf(v);
        // minimal escaping of Solr special chars
        return s.replaceAll("([+\\-!(){}\\[\\]^\"~*?:\\\\])", "\\\\$1");
    }

    private String formatRangeVal(Object v) {
        return String.valueOf(v); // extend for dates as needed
    }
}
