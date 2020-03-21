package com.stockquote.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;

import com.stockquote.serialization.StockQuoteDeserialization;

@EnableKafka
@Configuration
@PropertySource(value = "classpath:kafka.properties")
public class KafkaConsumerConfig {

	@Value("${kafka.bootstrap.server}")
	private String bootStrapAddress;

	@Value("kafka.consumer.groupId")
	private String groupId;

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> consumerProps = new HashMap<>();

		consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapAddress);
		consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StockQuoteDeserialization.class);
		consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

		return new DefaultKafkaConsumerFactory<>(consumerProps);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setConcurrency(3);
		factory.setErrorHandler(new SeekToCurrentErrorHandler());
		factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
		return factory;
	}

}
