package com.tuc.search;

import com.tuc.search.clause.BooleanClause;
import com.tuc.search.clause.TermClause;
import com.tuc.search.core.SearchQuery;
import com.tuc.search.core.SearchQueryBuilder;
import com.tuc.search.engine.SearchEngine;
import com.tuc.search.exception.SearchException;
import com.tuc.search.model.SearchResponse;
import com.tuc.search.model.SolrResponse;
import com.tuc.search.processor.QueryProcessor;
import com.tuc.search.processor.SolrQueryProcessor;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;
import java.util.List;

public class SearchService {
    
//    public SearchResults search(SearchCriteria criteria) {
//        SearchQueryBuilder builder = SearchQueryBuilder.forEngine(criteria.getEngine());
//
//        // Build query dynamically
//        if (criteria.getKeyword() != null) {
//            builder.match("content", criteria.getKeyword());
//        }
//
//        if (criteria.getDateRange() != null) {
//            builder.range("createdAt",
//                criteria.getDateRange().getStart(),
//                criteria.getDateRange().getEnd());
//        }
//
//        if (criteria.getFilters() != null) {
//            criteria.getFilters().forEach((field, value) ->
//                builder.filter(field, value)
//            );
//        }
//
//        if (criteria.getSortBy() != null) {
//            builder.sort(criteria.getSortBy(), criteria.getSortOrder());
//        }
//
//        builder.paginate(criteria.getPage(), criteria.getPageSize());
//
//        SearchQuery query = builder.build();
//
//        // Execute based on engine
//        return executeQuery(query);
//    }
//
//    private SearchResults executeQuery(SearchQuery query) {
//        switch (query.getEngine()) {
//            case ELASTICSEARCH:
//                return executeElasticsearchQuery(query);
//            case SOLR:
//                return executeSolrQuery(query);
//            case REDIS_SEARCH:
//                return executeRedisQuery(query);
//            default:
//                throw new UnsupportedOperationException();
//        }
//    }

    public static void main(String[] args) throws SearchException {
        SolrClient solrClient = null;/* HttpSolrClient or CloudSolrClient */;
        QueryProcessor processor = new SolrQueryProcessor(solrClient, "demographics");

        SearchQuery query = SearchQueryBuilder
                .forEngine(SearchEngine.REDIS_SEARCH)
                .bool(b -> b
                        .should(
                                new BooleanClause(
                                        List.of(
                                                new TermClause("name", "jhon"),
                                                new TermClause("age", "21")
                                        ),
                                        List.of(), List.of(), List.of()
                                )
                        )
                        .should(
                                new BooleanClause(
                                        List.of(
                                                new TermClause("name", "jhon"),
                                                new TermClause("pincode", "600021")
                                        ),
                                        List.of(), List.of(), List.of()
                                )
                        )
                )
                .build();

        SearchResponse response = processor.execute(query);


// response.getResults() → SolrDocumentList

    }
}