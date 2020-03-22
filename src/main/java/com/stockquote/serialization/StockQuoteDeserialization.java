package com.stockquote.serialization;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stockquote.common.Utility;
import com.stockquote.dto.StockQuote;
import com.stockquote.exception.EmptyStockQuoteDeserializationException;

public class StockQuoteDeserialization implements Deserializer<StockQuote> {

	@Override
	public StockQuote deserialize(String topic, byte[] stockQuoteBytes) {
		StockQuote quote=null;
		try{
			if(stockQuoteBytes == null)
				throw new EmptyStockQuoteDeserializationException("Empty Stock Quote cannot be Deserialized!!!");
			
			if(stockQuoteBytes.length == 0)
			    throw new EmptyStockQuoteDeserializationException("Empty Stock Quote Byte Array Length is zero cannot be Deserialized!!!");
			
			quote = Utility.XMLtoObject(new String(stockQuoteBytes));
		}catch (JsonProcessingException jsonexe){
			System.err.println("Error Deserilization : topic : " + topic + " : " + jsonexe.getMessage());
			throw new RuntimeException(jsonexe.getMessage(), jsonexe);
		}
		return quote;
	}

}
