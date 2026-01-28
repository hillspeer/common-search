package com.tuc.search.translator;

import com.tuc.search.engine.SearchEngine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class QueryTranslatorFactory {

    static Map<SearchEngine, QueryTranslator> translators = Map.of();


    public static Optional<QueryTranslator> getTranslator(SearchEngine engine){

        if(translators.containsKey(engine)){
            return Optional.of(translators.get(engine));
        }else{
            return Optional.empty();
        }

    }
}
