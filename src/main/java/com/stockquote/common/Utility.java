package com.stockquote.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.stockquote.dto.StockQuote;
import com.stockquote.entities.StockQuoteEntity;

public class Utility {

	public static String ObjecttoXML(StockQuote stockQuote) throws JsonProcessingException {
		XmlMapper xmlMapper = new XmlMapper();
		String stockQuoteInXml = xmlMapper.writeValueAsString(stockQuote);

		return stockQuoteInXml;
	}

	public static StockQuote XMLtoObject(String xmlStockQuote) throws JsonMappingException, JsonProcessingException {
		XmlMapper xmlMapper = new XmlMapper();
		return xmlMapper.readValue(xmlStockQuote, StockQuote.class);
	}

	public static String ObjecttoJson(StockQuote stockQuote) throws JsonProcessingException {
		ObjectMapper jsonMapper = new ObjectMapper();
		String stockQuoteinJson = jsonMapper.writeValueAsString(stockQuote);

		return stockQuoteinJson;
	}
	
	public static StockQuoteEntity JsontoEntity(String jsonStockQuote) throws JsonMappingException, JsonProcessingException{
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));
		return jsonMapper.readValue(jsonStockQuote, StockQuoteEntity.class);
	}
	
	public static Date timestamp() {
	    return new Date(ThreadLocalRandom.current().nextInt() * 1000L);
	}
}
