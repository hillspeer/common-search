package com.tuc.search;

import com.tuc.search.clause.MatchClause;
import com.tuc.search.clause.RangeOperator;
import com.tuc.search.clause.TermClause;
import com.tuc.search.core.SearchQuery;
import com.tuc.search.core.SearchQueryBuilder;
import com.tuc.search.core.SortOrder;
import com.tuc.search.engine.SearchEngine;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SearchQueryBuilderTest {

    @Test
    void testBasicMatchQuery() {
        SearchQuery query = SearchQueryBuilder
            .forEngine(SearchEngine.ELASTICSEARCH)
            .match("title", "elasticsearch")
            .build();
        
        assertNotNull(query);
        assertEquals(1, query.getClauses().size());
    }

    @Test
    void testComplexBoolQuery() {
        SearchQuery query = SearchQueryBuilder
            .forEngine(SearchEngine.SOLR)
            .bool(bool -> bool
                .must(new MatchClause("title", "database"))
                .should(new MatchClause("tags", "sql"))
                .filter(new TermClause("status", "published"))
            )
            .build();
        
        assertNotNull(query);
    }

    @Test
    void testRangeQuery() {
        SearchQuery query = SearchQueryBuilder
            .forEngine(SearchEngine.ELASTICSEARCH)
            .range("views", 100, 1000)
            .range("date", RangeOperator.GTE, LocalDate.of(2024, 1, 1))
            .build();
        
        assertEquals(2, query.getClauses().size());
    }

    @Test
    void testPagination() {
        SearchQuery query = SearchQueryBuilder
            .forEngine(SearchEngine.REDIS_SEARCH)
            .match("content", "test")
            .paginate(2, 20)
            .build();
        
        assertEquals(2, query.getPageable().getPage());
        assertEquals(20, query.getPageable().getPageSize());
        assertEquals(40, query.getPageable().getOffset());
    }

    @Test
    void testSorting() {
        SearchQuery query = SearchQueryBuilder
            .forEngine(SearchEngine.ELASTICSEARCH)
            .match("status", "active")
            .sort("timestamp", SortOrder.DESC)
            .sort("score", SortOrder.ASC)
            .build();
        
        assertEquals(2, query.getSorts().size());
    }
}