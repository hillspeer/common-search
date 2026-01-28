package com.tuc.search.processor;

import com.tuc.search.core.SearchQuery;
import com.tuc.search.engine.SearchEngine;
import com.tuc.search.exception.SearchException;
import com.tuc.search.model.RedisResponse;
import com.tuc.search.model.SearchResponse;
import com.tuc.search.model.SolrResponse;
import com.tuc.search.translator.RedisTranslator;
import com.tuc.search.translator.SolrTranslator;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.search.Query;
import redis.clients.jedis.search.SearchResult;

public class RedisCommandProcessor implements QueryProcessor{

    private final JedisCluster jedisClient;
    private final String indexName;
    private final RedisTranslator translator;

    public RedisCommandProcessor(JedisCluster solrClient, String indexName) {
        this.jedisClient = solrClient;
        this.indexName = indexName;
        this.translator = new RedisTranslator(); // from your framework
    }

    public SearchResponse execute(SearchQuery query) throws SearchException {

        if (query.getEngine() != SearchEngine.REDIS_SEARCH) {
            throw new IllegalArgumentException("SearchQuery is not for REDIS_SEARCH engine");
        }
        translator.setQuery(query);
        // 1) translate domain query → Solr q string
        Query qString = translator.buildRedisQuery(); // e.g. "(name:jhon AND age:21) OR ..."
        SearchResult result = jedisClient.ftSearch(indexName, qString);

        RedisResponse response = new RedisResponse();
        response.setResponse(result);

        return response;
    }

}
