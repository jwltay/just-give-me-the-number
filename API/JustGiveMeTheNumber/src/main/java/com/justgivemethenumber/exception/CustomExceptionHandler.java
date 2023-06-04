package com.justgivemethenumber.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@CrossOrigin
@ControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(value = {TickerNotFoundException.class})
	public final ResponseEntity<ExceptionResponse> handleTickerNotFoundException(TickerNotFoundException tickerNotFoundException, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), tickerNotFoundException.getMessage());
		return new ResponseEntity<ExceptionResponse> (exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {InvalidTickerException.class})
	public final ResponseEntity<ExceptionResponse> handleInvalidTickerException(InvalidTickerException invalidTickerException, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), invalidTickerException.getMessage());
		return new ResponseEntity<ExceptionResponse> (exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {ApiConnectionException.class})
	public final ResponseEntity<ExceptionResponse> handleApiConnectionException(ApiConnectionException ApiConnectionException, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ApiConnectionException.getMessage());
		return new ResponseEntity<ExceptionResponse> (exceptionResponse, HttpStatus.NOT_FOUND);
	}
}
