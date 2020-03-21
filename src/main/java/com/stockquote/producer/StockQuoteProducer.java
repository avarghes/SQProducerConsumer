package com.stockquote.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.stockquote.dto.StockQuote;

@Component
public class StockQuoteProducer {

	@Autowired
	private KafkaTemplate<String, StockQuote> kafkaTemplate;

	public ListenableFuture<SendResult<String, StockQuote>> sendStockQuote(StockQuote quote, String topicName,
			ListenableFutureCallback<SendResult<String, StockQuote>> stockQuoteListener) {

		ListenableFuture<SendResult<String, StockQuote>> stockQuoteFuture = kafkaTemplate.send(topicName, quote);

		stockQuoteFuture.addCallback(stockQuoteListener);

		return stockQuoteFuture;
	}

}
