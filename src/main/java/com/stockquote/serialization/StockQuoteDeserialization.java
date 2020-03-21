package com.stockquote.serialization;

import org.apache.kafka.common.serialization.Deserializer;
import com.stockquote.common.Utility;
import com.stockquote.dto.StockQuote;

public class StockQuoteDeserialization implements Deserializer<StockQuote> {

	@Override
	public StockQuote deserialize(String arg0, byte[] stockQuoteBytes) {
		// TODO Auto-generated method stub
		StockQuote quote=null;
		try{
			quote = Utility.XMLtoObject(new String(stockQuoteBytes));
		}catch (Exception exe){
			System.err.println("Error Deserilization : " + exe.getMessage());
		}
		return quote;
	}

}
