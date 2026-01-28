package com.tuc.search.exception;

import org.apache.solr.client.solrj.SolrServerException;

public class SearchException extends Throwable{

    public SearchException(SolrServerException e) {
        this.setStackTrace(e.getStackTrace());
    }

}
