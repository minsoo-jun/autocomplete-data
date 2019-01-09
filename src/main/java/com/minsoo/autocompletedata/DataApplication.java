package com.minsoo.autocompletedata;

import com.minsoo.autocompletedata.pubsub.SubscriberService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataApplication {

	public static void main(String[] args) {

		SpringApplication.run(DataApplication.class, args);
		try{
			SubscriberService.main("none");
		}catch(Exception e){

		}
	}

}

