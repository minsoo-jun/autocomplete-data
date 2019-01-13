package com.minsoo.autocompletedata;

import com.minsoo.autocompletedata.domain.ProductPubSub;
import org.springframework.integration.annotation.MessagingGateway;

/**
 * an interface that allows sending a person to Pub/Sub.
 */
@MessagingGateway(defaultRequestChannel = "pubSubOutputChannel")
public interface PubSubProductGateway {
    void sendProductToPubSub(ProductPubSub product);
}
