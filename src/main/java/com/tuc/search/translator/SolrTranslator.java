package com.tuc.search.translator;

import com.tuc.search.core.*;
import com.tuc.search.translator.QueryTranslator;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.stream.Collectors;

public final class SolrTranslator implements QueryTranslator {

    private SearchQuery query;

    @Override
    public void setQuery(SearchQuery query) {
        this.query = query;
    }

    @Override
    public String toQueryString() {
        if (query == null) throw new IllegalStateException("query not set");
        SolrClauseVisitor visitor = new SolrClauseVisitor();
        if (query.getClauses().isEmpty()) {
            return "*:*";
        }
        return query.getClauses().stream()
                .map(c -> c.accept(visitor))
                .collect(Collectors.joining(" AND "));
    }

    @Override
    public String toJson() {
        // for Solr you usually just send q + params; JSON here is optional
        return "{\"q\":\"" + toQueryString() + "\"}";
    }

    public SolrQuery buildSolrQuery() {
        SolrQuery solr = new SolrQuery();
        solr.setQuery(toQueryString());

        Pageable p = query.getPageable();
        solr.setStart(p.getOffset());
        solr.setRows(p.getPageSize());

        for (SortField sf : query.getSorts()) {
            solr.addSort(sf.getField(),
                    sf.getOrder() == SortOrder.ASC ? SolrQuery.ORDER.asc : SolrQuery.ORDER.desc);
        }
        return solr;
    }
}