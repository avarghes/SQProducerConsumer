package com.stockquote.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class StockQuote {
	private String ticker;
	private float openningPrice;
	private float closingPrice;
	private int volume;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date tradingDay;

	public StockQuote(String ticker, float openningPrice, float closingPrice, int volume, Date tradingDay) {
		this.ticker = ticker;
		this.openningPrice = openningPrice;
		this.closingPrice = closingPrice;
		this.volume = volume;
		this.tradingDay = tradingDay;
	}

	public StockQuote() {

	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public float getOpenningPrice() {
		return openningPrice;
	}

	public void setOpenningPrice(float openningPrice) {
		this.openningPrice = openningPrice;
	}

	public float getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(float closingPrice) {
		this.closingPrice = closingPrice;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public Date getTradingDay() {
		return tradingDay;
	}

	public void setTradingDay(Date tradingDay) {
		this.tradingDay = tradingDay;
	}
	
	@JsonIgnore
	public boolean isEmpty(){
		return this.ticker == null ? true : false;
	}

}
