package com.tuc.search.processor;

import com.tuc.search.core.SearchQuery;
import com.tuc.search.engine.SearchEngine;
import com.tuc.search.exception.SearchException;
import com.tuc.search.model.SearchResponse;
import com.tuc.search.model.SolrResponse;
import com.tuc.search.translator.SolrTranslator;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;

public class SolrQueryProcessor implements QueryProcessor{

    private final SolrClient solrClient;
    private final String collection;
    private final SolrTranslator translator;

    public SolrQueryProcessor(SolrClient solrClient, String collection) {
        this.solrClient = solrClient;
        this.collection = collection;
        this.translator = new SolrTranslator(); // from your framework
    }

    public SearchResponse execute(SearchQuery query) throws SearchException {

        if (query.getEngine() != SearchEngine.SOLR) {
            throw new IllegalArgumentException("SearchQuery is not for SOLR engine");
        }
        translator.setQuery(query);
        // 1) translate domain query → Solr q string
        String qString = translator.toQueryString(); // e.g. "(name:jhon AND age:21) OR ..."


        QueryResponse response = null;
        try {
            response = solrClient.query(collection, translator.buildSolrQuery());
        } catch (SolrServerException e) {
            throw new SearchException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SolrResponse solrResponse = new SolrResponse();
        solrResponse.setQueryResponse(response);

        SolrDocumentList docs = response.getResults();       // main docs
        long numFound = docs.getNumFound();                  // total matches

        for (SolrDocument doc : docs) {
            String id = (String) doc.getFieldValue("id");
            Object title = doc.getFieldValue("title");
            Float score = (Float) doc.getFieldValue("score");
            // use fields...
        }

        return solrResponse;
    }

}
