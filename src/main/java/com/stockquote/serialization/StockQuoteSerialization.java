package com.stockquote.serialization;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stockquote.common.Utility;
import com.stockquote.dto.StockQuote;
import com.stockquote.exception.EmptyStockQuoteSerializationException;

public class StockQuoteSerialization implements Serializer<StockQuote> {

	@Override
	public byte[] serialize(String topic, StockQuote quote) {
		byte[] xmlByteArray = null;
		try {
			if (quote.isEmpty())
				throw new EmptyStockQuoteSerializationException("Empty Stock Quote cannot be serialized!!!");

			String xmlQuote = Utility.ObjecttoXML(quote);
			xmlByteArray = xmlQuote.getBytes();
		} catch (JsonProcessingException jsonexe) {
			System.err.println("Error during Serialization : " + topic + " : " + jsonexe.getMessage());
			throw new RuntimeException(jsonexe.getMessage(),jsonexe);
		}
		return xmlByteArray;
	}

}
