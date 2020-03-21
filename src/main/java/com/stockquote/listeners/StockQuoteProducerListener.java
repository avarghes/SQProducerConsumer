package com.stockquote.listeners;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.stockquote.dto.StockQuote;

public class StockQuoteProducerListener implements ListenableFutureCallback<SendResult<String, StockQuote>> {

	@Override
	public void onSuccess(SendResult<String, StockQuote> stockQuoteResult) {
		System.out.println("Send Message with offset=[" + stockQuoteResult.getRecordMetadata().offset() + "]");
	}

	@Override
	public void onFailure(Throwable ex) {
		System.out.println("Unable to send message due to : " + ex.getMessage());
	}

}
