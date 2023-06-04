package com.justgivemethenumber.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.justgivemethenumber.exception.InvalidTickerException;
import com.justgivemethenumber.model.Ticker;
import com.justgivemethenumber.repository.TickerRepository;

class TickerServiceTest {
	
	@InjectMocks
	TickerService testService;
	
	@Mock
	private TickerRepository testTickerRepo;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testFindTickers() {
		  String tickerName = "AAPL";
		    Ticker expected = new Ticker(tickerName);
		    when(testTickerRepo.findByNameIs(tickerName)).thenReturn(Optional.of(expected));

		    Optional<Ticker> result = testService.findTicker(tickerName);

		    assertTrue(result.isPresent());
		    assertEquals(expected, result.get());
		    verify(testTickerRepo).findByNameIs(tickerName);
	}

	@Test
	void testFindTicker() {
		String tickerName = "AAPL";
		Ticker expected = new Ticker(tickerName);
		
		when(testTickerRepo.findByNameIs(tickerName)).thenReturn(Optional.of(expected));
		
		// run method
		Optional<Ticker> actual = testService.findTicker(tickerName);
		
		assertTrue(actual.isPresent());
		assertEquals(expected, actual.get());
		verify(testTickerRepo).findByNameIs(tickerName);
	}
	
	@Test
	void testCreateTicker() {
		String tickerName = "AAPL";
		Ticker expected = new Ticker(tickerName);
		
		when(testTickerRepo.save(any(Ticker.class))).thenReturn(expected);
		
		// run method
		Ticker actual = testService.createTicker(tickerName);
		
		assertEquals(expected, actual);
		verify(testTickerRepo).save(any(Ticker.class));
	}
	
	@Test
	void testUpdateTicker() {
		String oldTicker = "AAPL";
		String newTicker = "GOOG";
		
		Ticker ticker = new Ticker(oldTicker);
		when(testTickerRepo.findByNameIs(oldTicker)).thenReturn(Optional.of(ticker));
		
		
		testService.updateTicker(oldTicker, newTicker);
		
		assertEquals(newTicker, ticker.getName());
		verify(testTickerRepo).findByNameIs(oldTicker);
		verify(testTickerRepo).save(ticker);
		
	}

	@Test
	void testDeleteTicker() {
		String tickerName = "AAPL";
		
		testService.deleteTicker(tickerName);
		
		verify(testTickerRepo).deleteByName(tickerName);
	}

}
