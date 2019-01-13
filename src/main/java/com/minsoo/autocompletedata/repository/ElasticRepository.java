package com.minsoo.autocompletedata.repository;

import com.minsoo.autocompletedata.domain.EnDomain;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface ElasticRepository extends ElasticsearchRepository<EnDomain, String> {

    Optional<EnDomain> findById(String id);
    EnDomain save(EnDomain enDomain);

}
