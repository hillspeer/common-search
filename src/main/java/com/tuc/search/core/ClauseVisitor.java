package com.tuc.search.core;

import com.tuc.search.clause.*;

public interface ClauseVisitor<T> {
    T visitMatch(MatchClause clause);
    T visitRange(RangeClause clause);
    T visitBoolean(BooleanClause clause);
    T visitNested(NestedClause clause);
    //T visitFullText(FullTextClause clause);
    T visitTerm(TermClause clause);
//    T visitPrefix(PrefixClause clause);
//    T visitWildcard(WildcardClause clause);
}