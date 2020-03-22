package com.stockquote;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.Assert;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.util.concurrent.ListenableFuture;

import com.stockquote.common.Utility;
import com.stockquote.consumer.StockQuoteConsumer;
import com.stockquote.dto.StockQuote;
import com.stockquote.exception.EmptyStockQuoteSerializationException;
import com.stockquote.listeners.StockQuoteProducerListener;
import com.stockquote.producer.StockQuoteProducer;

@SpringBootTest
class SqProducerConsumerApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private Environment env;

	@Test
	void contextLoads() {
		assertNotNull(context, "Application Context is not null");
	}

	@Test
	void when_producer_writes_msg_to_topic() {
		StockQuoteProducer producer = context.getBean(StockQuoteProducer.class);

		Random stockPriceVolumeGenerator = new Random();

		StockQuote stockQuote = new StockQuote("TATASTEEL", stockPriceVolumeGenerator.nextFloat(),
				stockPriceVolumeGenerator.nextFloat(), stockPriceVolumeGenerator.nextInt(100000), Utility.timestamp());

		ListenableFuture<SendResult<String, StockQuote>> future = producer.sendStockQuote(stockQuote,
				env.getProperty("topicName"), context.getBean(StockQuoteProducerListener.class));

		SendResult<String, StockQuote> result = null;

		try {
			result = future.get();
		} catch (Exception exe) {
			System.err.println("Error writing the message");
		}

		assertNotNull(result, "Message written to topic " + result.getRecordMetadata().topic() + " offset : "
				+ result.getRecordMetadata().offset());

	}

	@Test()
	public void when_producer_writes_empty_msg_to_topic() {
		StockQuoteProducer producer = context.getBean(StockQuoteProducer.class);

		StockQuote stockQuote = new StockQuote();
		
		assertThrows(EmptyStockQuoteSerializationException.class, () -> {
			ListenableFuture<SendResult<String, StockQuote>> future = producer.sendStockQuote(stockQuote,
					env.getProperty("topicName"), context.getBean(StockQuoteProducerListener.class));
		});

	}

	@Test
	public void when_consumer_reads_msg_from_topic() throws InterruptedException {
		StockQuoteConsumer consumer = context.getBean(StockQuoteConsumer.class);

		assertNotNull(consumer, "Consumer reference is not null");

		Thread.sleep(5000);
	}

}
