package com.minsoo.autocompletedata.logic;

import com.minsoo.autocompletedata.domain.ProductPubSub;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

import static com.minsoo.autocompletedata.constants.Constants.TARGET_FIELD;
import static org.elasticsearch.index.query.QueryBuilders.*;

@Component
public class ElasticQueryBuilder {

    public QueryBuilder getQueryShouldBuilder(ProductPubSub params) {
        //search target input
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (!StringUtils.isEmpty(params.getId_sku())) {

            // add 'should match' query for the input string
            // fuzzyTranspositions https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-fuzzy-query.html
            boolQueryBuilder
                    .should(matchQuery(TARGET_FIELD, params.getId_sku()));

            // at-least one criteria has to be matched
            boolQueryBuilder.minimumShouldMatch(1);
        }
        return boolQueryBuilder;
    }

}
