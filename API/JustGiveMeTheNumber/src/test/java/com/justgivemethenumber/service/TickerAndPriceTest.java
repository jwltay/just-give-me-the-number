package com.justgivemethenumber.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.justgivemethenumber.exception.InvalidTickerException;
import com.justgivemethenumber.model.Ticker;
import com.justgivemethenumber.model.TickerAndPrice;

public class TickerAndPriceTest {
	
	TickerAndPriceService testService;
//	@Test
//	void testGetTickerAndPrice() {
//		String ticker = "AAPL";
//	   
//	    Map<String, BigDecimal> result = testService.getTickerAndPrice(ticker);
//
//	    assertTrue(result.get(ticker) instanceof BigDecimal);
//	    assertTrue(result.containsKey(ticker));
//	    assertThrows(InvalidTickerException.class, () -> testService.getTickerAndPrice("TIKR"));
//	}
	
	@BeforeEach
	void setUp() {
		this.testService = new TickerAndPriceService();
	}
	
	@Test
	void createTickerAndPrice() {
		TickerAndPrice expected = new TickerAndPrice("AAPL", new BigDecimal("100"));
		
		TickerAndPrice actual = testService.createTickerAndPrice("AAPL");
		
		assertEquals(expected.getTicker(), actual.getTicker());
		assertTrue(actual.getPrice() instanceof BigDecimal);
	}
	@Test
	void testGetAllTickersAndPrices() {
		List<Ticker> tickers = new ArrayList<>();
		tickers.add(new Ticker("AAPL"));
		tickers.add(new Ticker("GOOG"));
		
		List<TickerAndPrice> actual = testService.getAllTickersAndPrices(tickers);
		assertEquals(2, actual.size());
		assertTrue(actual.get(0).getTicker().equals(tickers.get(0).getName()));
		assertTrue(actual.get(1).getTicker().equals(tickers.get(1).getName()));
		assertTrue(actual.get(0).getPrice() instanceof BigDecimal);
	    assertTrue(actual.get(1).getPrice() instanceof BigDecimal);
	}
}
