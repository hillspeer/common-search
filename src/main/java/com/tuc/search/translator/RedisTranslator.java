package com.tuc.search.translator;

import com.tuc.search.core.*;
import redis.clients.jedis.search.Query;

import java.util.stream.Collectors;

public final class RedisTranslator implements QueryTranslator {

    private SearchQuery query;

    @Override
    public void setQuery(SearchQuery query) {
        this.query = query;
    }

    @Override
    public String toQueryString() {
        if (query == null) throw new IllegalStateException("query not set");
        RedisClauseVisitor visitor = new RedisClauseVisitor();
        if (query.getClauses().isEmpty()) {
            return "*";
        }
        return query.getClauses().stream()
                .map(c -> c.accept(visitor))
                .collect(Collectors.joining(" "));
    }

    @Override
    public String toJson() {
        return "{\"query\":\"" + toQueryString() + "\"}";
    }

    public Query buildRedisQuery() {
        String q = toQueryString();
        Query rq = new Query(q);

        Pageable p = query.getPageable();
        rq.limit(p.getOffset(), p.getPageSize());

        // NOTE: RediSearch only supports single SORTBY in FT.SEARCH
        if (!query.getSorts().isEmpty()) {
            SortField sf = query.getSorts().get(0);
            boolean asc = sf.getOrder() == SortOrder.ASC;
            rq.setSortBy(sf.getField(), asc);
        }

        if (query.hasHighlight()) {
            String[] fields = query.getHighlightFields().toArray(new String[0]);
            rq.highlightFields(fields);
        }

        return rq;
    }
}
