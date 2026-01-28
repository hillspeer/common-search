package com.tuc.search.processor;

import com.tuc.search.core.SearchQuery;
import com.tuc.search.engine.SearchEngine;
import com.tuc.search.translator.SolrTranslator;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

public class SolrQueryProcessor<QueryResponse> {

    private final SolrClient solrClient;
    private final String collection;
    private final SolrTranslator translator;

    public SolrQueryProcessor(SolrClient solrClient, String collection) {
        this.solrClient = solrClient;
        this.collection = collection;
        this.translator = new SolrTranslator(); // from your framework
    }

    public QueryResponse execute(SearchQuery query)  {
        if (query.getEngine() != SearchEngine.SOLR) {
            throw new IllegalArgumentException("SearchQuery is not for SOLR engine");
        }

        // 1) translate domain query → Solr q string
        String qString = translator.translate(query); // e.g. "(name:jhon AND age:21) OR ..."

        // 2) build SolrQuery
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(qString);                            // q
        solrQuery.setStart(query.getPageable().getOffset());    // start
        solrQuery.setRows(query.getPageable().getPageSize());   // rows

        // optional: sort
//        for (SortField sf : query.getSorts()) {
//            solrQuery.addSort(sf.getField(),
//                sf.getOrder() == SortOrder.ASC
//                    ? SolrQuery.ORDER.asc
//                    : SolrQuery.ORDER.desc);
//        }

        // 3) execute with SolrJ
        return solrClient.query(collection, solrQuery);
    }
}
