package com.stockquote.serialization;

import org.apache.kafka.common.serialization.Serializer;

import com.stockquote.common.Utility;
import com.stockquote.dto.StockQuote;

public class StockQuoteSerialization implements Serializer<StockQuote> {

	@Override
	public byte[] serialize(String arg0, StockQuote quote) {
		byte[] xmlByteArray = null;
		try{
			String xmlQuote = Utility.ObjecttoXML(quote);
			xmlByteArray = xmlQuote.getBytes();
		}catch(Exception e)	{
			System.err.println("Error during Serialization : " + e.getMessage());
		}
		return xmlByteArray;
	}

}
