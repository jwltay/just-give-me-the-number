package com.justgivemethenumber.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justgivemethenumber.model.Ticker;
import com.justgivemethenumber.repository.TickerRepository;

@Service
public class TickerService {
	
	@Autowired
	private TickerRepository tickerRepo;
	
	public List<Ticker> findTickers() {
		return (List<Ticker>) tickerRepo.findAll();
	}
	
	public Optional<Ticker> findTicker(String ticker) {
		return tickerRepo.findByNameIs(ticker);
	}
	
	public Ticker createTicker(String ticker) {
		Ticker t = new Ticker(ticker.toUpperCase());
		tickerRepo.save(t);
		
		return t;
	}
	
	public void updateTicker(String oldTicker, String newTicker) {
		Ticker ticker = tickerRepo.findByNameIs(oldTicker).get();
		ticker.setName(newTicker.toUpperCase());
		tickerRepo.save(ticker);
	}
	
	public void deleteTicker(String ticker) {
		tickerRepo.deleteByName(ticker);
	}

}
