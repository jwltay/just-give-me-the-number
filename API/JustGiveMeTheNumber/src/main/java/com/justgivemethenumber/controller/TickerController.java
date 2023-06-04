package com.justgivemethenumber.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justgivemethenumber.util.ResponseHandler;
import com.justgivemethenumber.exception.ApiConnectionException;
import com.justgivemethenumber.exception.TickerNotFoundException;
import com.justgivemethenumber.model.Ticker;
import com.justgivemethenumber.model.TickerAndPrice;
import com.justgivemethenumber.service.TickerAndPriceService;
import com.justgivemethenumber.service.TickerService;

@CrossOrigin
@RestController
@RequestMapping("/")
public class TickerController {
	
	@Autowired
	TickerService tickerService;
	@Autowired
	TickerAndPriceService tickerAndPriceService;
	
	@GetMapping
	public ResponseEntity<Object> getAllTickers() throws ApiConnectionException {
		List<TickerAndPrice> tickersAndPrices = tickerAndPriceService.getAllTickersAndPrices(tickerService.findTickers());
		return ResponseHandler.generateResponse("Prices retrieved.", HttpStatus.OK, tickersAndPrices);
		
	}
	
	@GetMapping("/{ticker}")
	public ResponseEntity<Object> getTicker(@PathVariable("ticker") String ticker) throws TickerNotFoundException, ApiConnectionException {
		ResponseEntity<Object> responseEntity;
		
		Optional<Ticker> t = tickerService.findTicker(ticker);
		
		if (!t.isPresent())
			throw new TickerNotFoundException("Ticker not found: " + ticker + ". Please add tickers of the stocks you woud like to track.");
		else {
			TickerAndPrice tickerAndPrice = tickerAndPriceService.createTickerAndPrice(t.get().getName());
			responseEntity = ResponseHandler.generateResponse("Price retrieved.", HttpStatus.OK, tickerAndPrice);
		}
		return responseEntity;
	}
	
	@PostMapping
	public ResponseEntity<Object> addTicker(@RequestBody String name) {
		try {
			Ticker ticker = tickerService.createTicker(name);
			return ResponseHandler.generateResponse("Ticker added.", HttpStatus.CREATED, ticker);
		} catch (Exception e){
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}
	
	@PatchMapping("/{ticker}")
	public ResponseEntity<Object> updateTicker(@PathVariable("ticker") String oldTicker, @RequestBody String newTicker) {
		try {
			tickerService.updateTicker(oldTicker, newTicker);
			return ResponseHandler.generateResponse("Ticker updated.", HttpStatus.OK, tickerService.findTicker(newTicker).get());
		} catch (Exception e){
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}
	
	@DeleteMapping("/{ticker}")
	public ResponseEntity<Object> deleteTicker(@PathVariable("ticker") String ticker) {
		try {
			tickerService.deleteTicker(ticker);
			return ResponseHandler.generateResponse("Ticker deleted.", HttpStatus.NO_CONTENT, null);
		} catch (Exception e){
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}
}
