package com.justgivemethenumber.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.justgivemethenumber.exception.ApiConnectionException;
import com.justgivemethenumber.exception.InvalidTickerException;
import com.justgivemethenumber.model.Ticker;
import com.justgivemethenumber.model.TickerAndPrice;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class TickerAndPriceService {
	public TickerAndPrice createTickerAndPrice(String ticker)  throws ApiConnectionException, InvalidTickerException {
		TickerAndPrice tickerAndPrice = new TickerAndPrice(ticker, fetchPrice(ticker));
		// try catch
		return tickerAndPrice;
	}
	
	public List<TickerAndPrice> getAllTickersAndPrices(List<Ticker> tickers) throws ApiConnectionException, InvalidTickerException {
		return tickers.stream()
			.map(ticker -> createTickerAndPrice(ticker.getName()))
			.collect(Collectors.toList());
	}
	
	private BigDecimal fetchPrice(String ticker) throws ApiConnectionException, InvalidTickerException {
		String url = "https://realstonks.p.rapidapi.com/" + ticker;
		
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
			.url(url)
			.get()
			.addHeader("X-RapidAPI-Key", "36734d31bcmshd18c578ef51111cp1fcca1jsnf42ba3e45311")
			.addHeader("X-RapidAPI-Host", "realstonks.p.rapidapi.com")
			.build();
		
		String responseString = "";

		Response response;
		try {
			response = client.newCall(request).execute();
			responseString = response.body().string();
		} catch (IOException e) {
			throw new ApiConnectionException("Connection to API failed.");
		}
		
		return extractPrice(responseString, ticker);
	}
	

	private BigDecimal extractPrice(String responseString, String ticker) {
		BigDecimal price = new BigDecimal("0");
		
		if (responseString.equals("{\"message\":\"API doesn't exists\"}"))
			throw new ApiConnectionException("Connection to API failed.");
		if (responseString.split(" ").length == 3) 
			throw new InvalidTickerException("Invalid ticker encountered: " + ticker + ". Please use a valid NASDAQ-listed stock ticker.");
		
		String responseStringWithoutQuotesAndCommas = responseString.replace("\\\"", "").replace(", ", " ");
		String[] items = responseStringWithoutQuotesAndCommas.split(" ");
		price = new BigDecimal(items[1]);
		
		return price;
	}
}
