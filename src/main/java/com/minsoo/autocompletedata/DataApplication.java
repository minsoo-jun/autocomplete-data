package com.minsoo.autocompletedata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsoo.autocompletedata.pubsub.SubscriberService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gcp.pubsub.support.converter.JacksonPubSubMessageConverter;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DataApplication {

	public static void main(String[] args) { SpringApplication.run(DataApplication.class, args); }

    /**
     * This bean enables serialization/deserialization of Java objects to JSON allowing you
     * utilize JSON message payloads in Cloud Pub/Sub.
     * @param objectMapper the object mapper to use
     * @return a Jackson message converter
     */
    @Bean
    public JacksonPubSubMessageConverter jacksonPubSubMessageConverter(ObjectMapper objectMapper) {
        return new JacksonPubSubMessageConverter(objectMapper);
    }

}

