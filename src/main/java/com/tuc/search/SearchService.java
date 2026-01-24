package com.tuc.search.phl;

public class SearchService {
    
    public SearchResults search(SearchCriteria criteria) {
        SearchQueryBuilder builder = SearchQueryBuilder.forEngine(criteria.getEngine());
        
        // Build query dynamically
        if (criteria.getKeyword() != null) {
            builder.match("content", criteria.getKeyword());
        }
        
        if (criteria.getDateRange() != null) {
            builder.range("createdAt", 
                criteria.getDateRange().getStart(),
                criteria.getDateRange().getEnd());
        }
        
        if (criteria.getFilters() != null) {
            criteria.getFilters().forEach((field, value) -> 
                builder.filter(field, value)
            );
        }
        
        if (criteria.getSortBy() != null) {
            builder.sort(criteria.getSortBy(), criteria.getSortOrder());
        }
        
        builder.paginate(criteria.getPage(), criteria.getPageSize());
        
        SearchQuery query = builder.build();
        
        // Execute based on engine
        return executeQuery(query);
    }
    
    private SearchResults executeQuery(SearchQuery query) {
        switch (query.getEngine()) {
            case ELASTICSEARCH:
                return executeElasticsearchQuery(query);
            case SOLR:
                return executeSolrQuery(query);
            case REDIS_SEARCH:
                return executeRedisQuery(query);
            default:
                throw new UnsupportedOperationException();
        }
    }
}