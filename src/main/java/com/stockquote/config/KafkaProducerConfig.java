package com.stockquote.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.stockquote.dto.StockQuote;
import com.stockquote.listeners.StockQuoteProducerListener;
import com.stockquote.serialization.StockQuoteSerialization;

@Configuration
@PropertySource(value = "classpath:kafka.properties")
public class KafkaProducerConfig {

	@Value("${kafka.bootstrap.server}")
	private String bootStrapAddress;

	@Bean
	public ProducerFactory<String, StockQuote> producerFactory() {
		Map<String, Object> producerConfig = new HashMap<>();
		producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapAddress);
		producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StockQuoteSerialization.class);
		return new DefaultKafkaProducerFactory<String,StockQuote>(producerConfig);
	}

	@Bean
	public KafkaTemplate<String, StockQuote> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public StockQuoteProducerListener stockQuoteListener(){
		return new StockQuoteProducerListener();
	}
}
