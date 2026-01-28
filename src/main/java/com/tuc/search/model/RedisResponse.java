package com.tuc.search.model;

import lombok.Data;
import org.apache.solr.client.solrj.response.QueryResponse;
import redis.clients.jedis.search.SearchResult;

@Data
public class RedisResponse implements SearchResponse{

    SearchResult response;

    public void setResponse(SearchResult response) {
        this.response = response;
    }
}
