package com.eauction.config;

import org.axonframework.extensions.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.extensions.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AmqpConfig {
	
	@Value("${axon.amqp.exchange}")
	private String exchange;
	
	@Value("${eauction.rabbitmq.queue}")
	private String queueName;

	@Bean
	public Exchange eventsExchange() {
		return ExchangeBuilder.topicExchange(exchange).build();
	}

	@Bean
	public Queue eventsQueue() {
		return QueueBuilder.durable(queueName).build();
	}

	@Bean
	public Binding eventsBinding() {
		return BindingBuilder.bind(eventsQueue()).to(eventsExchange()).with("#").noargs();
	}
	
	@Bean
	public SpringAMQPMessageSource eventsQueueMessageSource(AMQPMessageConverter messageConverter) {
		return new SpringAMQPMessageSource(messageConverter) {
			@Override
			@RabbitListener(queues = "${eauction.rabbitmq.queue}")
			public void onMessage(Message message, Channel channel) {
				log.info("AMQP event message received: {}", message);
				super.onMessage(message, channel);
			}
		};
	}
}
