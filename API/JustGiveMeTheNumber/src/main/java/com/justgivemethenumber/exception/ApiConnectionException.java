package com.justgivemethenumber.exception;

public class ApiConnectionException extends RuntimeException {
	public ApiConnectionException(String details) {
		super(details);
	}
}
