package com.eauction.config;

import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.extensions.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventProcessingConfig {
	
	@Autowired
	public void configure(EventProcessingConfigurer epConfig, SpringAMQPMessageSource auctionMessageSource) {
	    epConfig.registerSubscribingEventProcessor("auctionEventProcessor", c -> auctionMessageSource);
	}
}
