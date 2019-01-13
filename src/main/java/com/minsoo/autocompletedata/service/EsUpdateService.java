package com.minsoo.autocompletedata.service;

import com.minsoo.autocompletedata.domain.EnDomain;
import com.minsoo.autocompletedata.domain.ProductPubSub;
import com.minsoo.autocompletedata.logic.ElasticQueryBuilder;
import com.minsoo.autocompletedata.repository.ElasticRepository;
import org.apache.commons.lang3.time.StopWatch;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.minsoo.autocompletedata.constants.Constants.DOCUMENT_TYPE;
import static com.minsoo.autocompletedata.constants.Constants.TARGET_EN_INDEX;

@Service
public class EsUpdateService {

    @Autowired
    private ElasticServiceImpl elasticService;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    public void searchDocuments(ProductPubSub productPubSub) {
        StopWatch sw = new StopWatch();
        sw.start();
        Optional<EnDomain> oldData = elasticService.findById(productPubSub.getId_sku()+"");
        EnDomain domain = oldData.get();
        System.out.println("###########################");
        System.out.println(domain.getSku());
        System.out.println(domain.getName());
        System.out.println(domain.getPopurality());
        System.out.println("###########################");
        domain.setName(productPubSub.getN_product());
        domain.setPopurality(productPubSub.getN_popurality());
        System.out.println(elasticService.save(domain));


        sw.stop();
        System.out.println(sw.getTime() + "ms");


    }
}
