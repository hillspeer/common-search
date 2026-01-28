package com.tuc.search.core;

import com.tuc.search.aggregation.AggregationSpec;
import com.tuc.search.clause.*;
import com.tuc.search.engine.SearchEngine;
import com.tuc.search.translator.QueryTranslator;
import com.tuc.search.translator.QueryTranslatorFactory;

import java.util.*;
import java.util.function.Consumer;

public class SearchQueryBuilder {
    private final SearchEngine engine;
    private final List<Clause> clauses;
    private final Map<String, AggregationSpec> aggregations;
    private final List<SortField> sorts;
    private Pageable pageable;
    private final Set<String> highlightFields;

    private SearchQueryBuilder(SearchEngine engine) {
        this.engine = engine;
        this.clauses = new ArrayList<>();
        this.aggregations = new LinkedHashMap<>();
        this.sorts = new ArrayList<>();
        this.highlightFields = new HashSet<>();
    }

    public static SearchQueryBuilder forEngine(SearchEngine engine) {
        if (engine == null) {
            throw new IllegalArgumentException("Engine cannot be null");
        }
        return new SearchQueryBuilder(engine);
    }

    // MATCH QUERIES
    public SearchQueryBuilder match(String field, String value) {
        validateField(field);
        clauses.add(new MatchClause(field, value));
        return this;
    }

    public SearchQueryBuilder matchPhrase(String field, String phrase) {
        validateField(field);
        clauses.add(new MatchClause(field, phrase, true));
        return this;
    }

    public SearchQueryBuilder match(String field, String value, float boost) {
        validateField(field);
        MatchClause clause = new MatchClause(field, value);
        clause.setBoost(boost);
        clauses.add(clause);
        return this;
    }

    // TERM QUERIES (exact match)
    public SearchQueryBuilder term(String field, Object value) {
        validateField(field);
        clauses.add(new TermClause(field, value));
        return this;
    }

    // RANGE QUERIES
    public SearchQueryBuilder range(String field, Object from, Object to) {
        validateField(field);
        clauses.add(new RangeClause(field, from, to));
        return this;
    }

    public SearchQueryBuilder range(String field, RangeOperator operator, Object value) {
        validateField(field);
        RangeClause clause = new RangeClause(field, operator, value);
        clauses.add(clause);
        return this;
    }

    // BOOLEAN QUERIES
    public SearchQueryBuilder must(Clause clause) {
        if (clause == null) throw new IllegalArgumentException("Clause cannot be null");
        clauses.add(clause);
        return this;
    }

    public SearchQueryBuilder bool(Consumer<BooleanQueryBuilder> consumer) {
        BooleanQueryBuilder boolBuilder = new BooleanQueryBuilder();
        consumer.accept(boolBuilder);
        clauses.add(boolBuilder.build());
        return this;
    }

    // FILTER (treated as must in bool context)
    public SearchQueryBuilder filter(String field, Object value) {
        validateField(field);
        clauses.add(new TermClause(field, value, ClauseContext.FILTER));
        return this;
    }

    // NESTED QUERIES (for Elasticsearch mainly)
    public SearchQueryBuilder nested(String path, Consumer<SearchQueryBuilder> consumer) {
        SearchQueryBuilder nestedBuilder = new SearchQueryBuilder(engine);
        consumer.accept(nestedBuilder);
        clauses.add(new NestedClause(path, nestedBuilder.clauses));
        return this;
    }

    // FULL TEXT SEARCH
//    public SearchQueryBuilder fullText(String field, String query) {
//        validateField(field);
//        clauses.add(new FullTextClause(field, query));
//        return this;
//    }

    // SORTING
    public SearchQueryBuilder sort(String field, SortOrder order) {
        validateField(field);
        sorts.add(new SortField(field, order));
        return this;
    }

    public SearchQueryBuilder sort(String field, SortOrder order, boolean unmapped) {
        validateField(field);
        SortField sortField = new SortField(field, order);
        sortField.setUnmappedType("long"); // Default for numeric
        sorts.add(sortField);
        return this;
    }

    // PAGINATION
    public SearchQueryBuilder paginate(int page, int pageSize) {
        if (page < 0) throw new IllegalArgumentException("Page must be >= 0");
        if (pageSize <= 0) throw new IllegalArgumentException("PageSize must be > 0");
        this.pageable = new Pageable(page, pageSize);
        return this;
    }

    // AGGREGATIONS
    public SearchQueryBuilder aggregateBy(String name, AggregationSpec spec) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Aggregation name cannot be empty");
        }
        if (spec == null) {
            throw new IllegalArgumentException("AggregationSpec cannot be null");
        }
        aggregations.put(name, spec);
        return this;
    }

    // HIGHLIGHTING
    public SearchQueryBuilder highlight(String... fields) {
        if (fields != null) {
            Collections.addAll(highlightFields, fields);
        }
        return this;
    }

    public SearchQuery build() {
        //QueryTranslator translator = QueryTranslatorFactory.getTranslator(engine).get();
        return new SearchQueryImpl(
            engine,
            Collections.unmodifiableList(clauses),
            Collections.unmodifiableMap(aggregations),
            Collections.unmodifiableList(sorts),
            pageable != null ? pageable : new Pageable(0, 10),
            Collections.unmodifiableSet(highlightFields)
        );
    }

    private void validateField(String field) {
        if (field == null || field.isEmpty()) {
            throw new IllegalArgumentException("Field cannot be null or empty");
        }
    }
}