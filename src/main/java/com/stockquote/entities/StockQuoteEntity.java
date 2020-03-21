package com.stockquote.entities;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "stockquote")
public class StockQuoteEntity {
	@PrimaryKeyColumn(name = "id",ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private UUID id;

	@Column("stockticker")
	private String ticker;

	@Column("stockopenprice")
	private float openningPrice;

	@Column("stockclosingprice")
	private float closingPrice;

	@Column("stockvolume")
	private int volume;

	@Column("stocktradingday")
	private Date tradingDay;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "StockQuoteEntity [getId()=" + getId() + ", getTicker()=" + getTicker() + ", getOpenningPrice()="
				+ getOpenningPrice() + ", getClosingPrice()=" + getClosingPrice() + ", getVolume()=" + getVolume()
				+ ", getTradingDay()=" + getTradingDay() + "]";
	}

}
