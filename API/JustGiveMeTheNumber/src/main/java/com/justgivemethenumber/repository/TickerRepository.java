package com.justgivemethenumber.repository;

import com.justgivemethenumber.model.*;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerRepository extends JpaRepository<Ticker, Integer> {
	Optional<Ticker> findByNameIs(String name);
	@Modifying
	@Transactional
	long deleteByName(String name);
}
