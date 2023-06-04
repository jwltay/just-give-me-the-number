package com.justgivemethenumber.exception;

public class InvalidTickerException extends RuntimeException {
	public InvalidTickerException(String details) {
		super(details);
	}
}
