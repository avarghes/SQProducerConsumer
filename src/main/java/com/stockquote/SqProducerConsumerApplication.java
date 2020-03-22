package com.stockquote;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.stockquote.common.Utility;
import com.stockquote.dto.StockQuote;
import com.stockquote.listeners.StockQuoteProducerListener;
import com.stockquote.producer.StockQuoteProducer;

@SpringBootApplication
public class SqProducerConsumerApplication implements CommandLineRunner {


	@Autowired
	private StockQuoteProducer producer;
	
	@Autowired
	private StockQuoteProducerListener listener;

	public static void main(String[] args) {
		SpringApplication.run(SqProducerConsumerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		String topicName = "stockexchange";
//		Random stockPriceVolumeGenerator = new Random();
//
//		for (int i = 0; i <= 10; i++) {
//
//			StockQuote stockQuote = new StockQuote("TATASTEEL", stockPriceVolumeGenerator.nextFloat(),
//					stockPriceVolumeGenerator.nextFloat(), stockPriceVolumeGenerator.nextInt(100000),
//					Utility.timestamp());
//
//			producer.sendStockQuote(stockQuote, topicName,listener);
//		}
	}

}
