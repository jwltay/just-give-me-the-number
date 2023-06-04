package com.justgivemethenumber.model;

import java.math.BigDecimal;
import java.util.Objects;

public class TickerAndPrice {
	private String ticker;
	private BigDecimal price;
	
	public TickerAndPrice() {}

	public TickerAndPrice(String ticker, BigDecimal price) {
		super();
		this.ticker = ticker;
		this.price = price;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(price, ticker);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TickerAndPrice other = (TickerAndPrice) obj;
		return Objects.equals(price, other.price) && Objects.equals(ticker, other.ticker);
	}

	@Override
	public String toString() {
		return "TickerAndPrice [ticker=" + ticker + ", price=" + price + "]";
	}
}
