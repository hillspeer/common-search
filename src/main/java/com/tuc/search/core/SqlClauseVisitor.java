package com.tuc.search.core;

import com.tuc.search.clause.MatchClause;
import com.tuc.search.clause.TermClause;

public class SqlClauseVisitor {//implements ClauseVisitor<String> {
//    private final SqlBuilder sql;
//
//    @Override
//    public String visitMatch(MatchClause clause) {
//        // Full-text search if available, else LIKE
//        if (supportsFullText()) {
//            return String.format("MATCH(%s) AGAINST(? IN NATURAL LANGUAGE MODE)",
//                               quote(clause.getFieldName()));
//        } else {
//            return String.format("%s LIKE ?", quote(clause.getFieldName()));
//        }
//    }
//
//    @Override
//    public String visitTerm(TermClause clause) {
//        return String.format("%s = ?", quote(clause.getFieldName()));
//    }
//
//    @Override
//    public String visitRange(RangeClause clause) {
//        if (clause.getFrom() != null && clause.getTo() != null) {
//            return String.format("%s BETWEEN ? AND ?",
//                               quote(clause.getFieldName()));
//        } else if (clause.getFrom() != null) {
//            return String.format("%s >= ?", quote(clause.getFieldName()));
//        } else {
//            return String.format("%s <= ?", quote(clause.getFieldName()));
//        }
//    }
//
//    @Override
//    public String visitBoolean(BooleanClause clause) {
//        List<String> conditions = new ArrayList<>();
//
//        if (!clause.getMust().isEmpty()) {
//            conditions.add("AND " + joinClauses(clause.getMust()));
//        }
//        if (!clause.getFilter().isEmpty()) {
//            conditions.add("AND " + joinClauses(clause.getFilter()));
//        }
//        if (!clause.getShould().isEmpty()) {
//            conditions.add("OR " + joinClauses(clause.getShould()));
//        }
//
//        return "(" + String.join(" ", conditions) + ")";
//    }
}
