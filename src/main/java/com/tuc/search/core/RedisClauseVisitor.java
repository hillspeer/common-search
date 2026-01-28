package com.tuc.search.core;

import com.tuc.search.clause.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RedisClauseVisitor implements ClauseVisitor<String> {

    @Override
    public String visitTerm(TermClause c) {
        // exact/tag match: @field:{value}
        String f = c.getFieldName();
        String v = escape(c.getValue());
        return "@" + f + ":{" + v + "}";
    }

    @Override
    public String visitMatch(MatchClause c) {
        String f = c.getFieldName();
        String v = escape(c.getValue());

        String expr;
        if (c.isPhrase()) {
            expr = "@" + f + ":\"" + v + "\"";              // phrase
        } else {
            String[] terms = v.split("\\s+");
            if (terms.length == 1) {
                expr = "@" + f + ":" + v;                  // single term
            } else {
                // OR across terms: @field:(t1|t2)
                expr = "@" + f + ":(" + String.join("|", terms) + ")";
            }
        }
        if (c.getBoost() != 1.0f) {
            expr = "(" + expr + ")^" + c.getBoost();       // RediSearch boosting
        }
        return expr;
    }

    @Override
    public String visitRange(RangeClause c) {
        String f = c.getFieldName();
        String from = c.getFrom() == null ? "-inf" : formatRangeVal(c.getFrom());
        String to   = c.getTo()   == null ? "+inf" : formatRangeVal(c.getTo());

        // RediSearch uses [ / ( for inclusive/exclusive bounds
        String lb = c.isIncludeFrom() ? "[" : "(";
        String rb = c.isIncludeTo()   ? "]" : ")";

        return "@" + f + ":" + lb + from + " " + to + rb;  // @f:[min max]
    }

    @Override
    public String visitBoolean(BooleanClause b) {
        List<String> parts = new ArrayList<>();

        if (!b.getMust().isEmpty()) {
            String must = b.getMust().stream()
                .map(cl -> cl.accept(this))
                .collect(Collectors.joining(" "));
            parts.add("(" + must + ")");                    // space = AND
        }

        if (!b.getFilter().isEmpty()) {
            String filter = b.getFilter().stream()
                .map(cl -> cl.accept(this))
                .collect(Collectors.joining(" "));
            parts.add("(" + filter + ")");
        }

        if (!b.getShould().isEmpty()) {
            String should = b.getShould().stream()
                .map(cl -> cl.accept(this))
                .collect(Collectors.joining(" | "));        // | = OR
            parts.add("(" + should + ")");
        }

        if (!b.getMustNot().isEmpty()) {
            String not = b.getMustNot().stream()
                .map(cl -> "-(" + cl.accept(this) + ")")    // -expr = NOT
                .collect(Collectors.joining(" "));
            parts.add(not);
        }

        if (parts.isEmpty()) return "*";
        return "(" + String.join(" ", parts) + ")";
    }

    @Override
    public String visitNested(NestedClause clause) {
        return "";
    }

    // nested/fulltext/prefix/wildcard similar, following RediSearch docs…

    private String escape(Object v) {
        String s = String.valueOf(v).trim();
        // escape ", |, -, (), {} as per docs
        return s.replaceAll("([\"|\\-(){}])", "\\\\$1");
    }

    private String formatRangeVal(Object v) {
        return String.valueOf(v);
    }
}
