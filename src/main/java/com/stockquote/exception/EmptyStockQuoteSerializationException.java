package com.stockquote.exception;

public class EmptyStockQuoteSerializationException extends RuntimeException {
	
	private static final long serialVersionUID = -3931161668479691924L;

	public EmptyStockQuoteSerializationException(String message){
		super(message);
	}

}
