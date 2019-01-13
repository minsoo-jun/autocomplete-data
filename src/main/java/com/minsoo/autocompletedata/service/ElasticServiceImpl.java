package com.minsoo.autocompletedata.service;

import com.minsoo.autocompletedata.domain.EnDomain;
import com.minsoo.autocompletedata.repository.ElasticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;
import java.util.Optional;

@Service
public class ElasticServiceImpl {

    private ElasticRepository elasticRepository;

    @Autowired
    public void setElasticRepository(ElasticRepository elasticRepository){
        this.elasticRepository = elasticRepository;
    }

    public EnDomain save(EnDomain enDomain){
        return elasticRepository.save(enDomain);
    }

    public Optional<EnDomain> findById(String id){
        return elasticRepository.findById(id);
    }
}
