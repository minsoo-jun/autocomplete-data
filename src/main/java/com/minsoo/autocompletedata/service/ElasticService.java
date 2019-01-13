package com.minsoo.autocompletedata.service;

import com.minsoo.autocompletedata.domain.EnDomain;

public interface ElasticService {
    EnDomain save(EnDomain enDomain);
    EnDomain findById(String id);

}
