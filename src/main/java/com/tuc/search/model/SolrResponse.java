package com.tuc.search.model;

import lombok.Data;
import org.apache.solr.client.solrj.response.QueryResponse;

@Data
public class SolrResponse implements SearchResponse{

    QueryResponse response;

    public void setQueryResponse(QueryResponse response) {
    }
}
