package com.stockquote.exception;

public class EmptyStockQuoteDeserializationException extends RuntimeException {
	
	private static final long serialVersionUID = 1003069027445340783L;

	public EmptyStockQuoteDeserializationException(String message){
		super(message);
	}

}
