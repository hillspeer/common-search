package com.tuc.search.translator;

import com.tuc.search.core.SearchQuery;

public class SqlTranslator implements QueryTranslator {
    private final SearchQuery query;
    private final Dialect dialect; // MySQL, PostgreSQL, SQL Server, etc.
    
    @Override
    public String translate(SearchQuery query) {
        this.query = query;
        SqlBuilder sql = new SqlBuilder(dialect);
        
        // SELECT clause with aggregations
        sql.select(buildSelectClause());
        
        // FROM clause
        sql.from("your_table"); // configurable
        
        // WHERE clause from clauses
        for (Clause clause : query.getClauses()) {
            clause.accept(new SqlClauseVisitor(sql));
        }
        
        // ORDER BY
        if (!query.getSorts().isEmpty()) {
            sql.orderBy(buildOrderByClause());
        }
        
        // LIMIT/OFFSET
        if (query.getPageable() != null) {
            sql.limit(query.getPageable().getOffset(), query.getPageable().getPageSize());
        }
        
        return sql.toString();
    }
}
