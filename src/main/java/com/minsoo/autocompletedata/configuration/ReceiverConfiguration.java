package com.minsoo.autocompletedata.configuration;

import com.minsoo.autocompletedata.domain.Product;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;

import java.util.ArrayList;

@Configuration
public class ReceiverConfiguration {
    private static final Log LOGGER = LogFactory.getLog(ReceiverConfiguration.class);

    private static final String SUBSCRIPTION_NAME = "product_sub";

    private final ArrayList<Product> processedProductList = new ArrayList<>();

    @Bean
    public DirectChannel pubSubInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("pubSubInputChannel") MessageChannel inputChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, SUBSCRIPTION_NAME);
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.MANUAL);
        adapter.setPayloadType(Product.class);
        return adapter;
    }

    @ServiceActivator(inputChannel = "pubSubInputChannel")
    public void messageReceiver(Product payload,
                                @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        LOGGER.info("Message arrived! Payload: " + payload);
        this.processedProductList.add(payload);
        message.ack();
    }

    @Bean
    @Qualifier("ProcessedProductList")
    public ArrayList<Product> processedPorductList() {
        return this.processedProductList;
    }
}
