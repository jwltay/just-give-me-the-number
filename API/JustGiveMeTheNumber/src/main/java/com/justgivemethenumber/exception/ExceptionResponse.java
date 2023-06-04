package com.justgivemethenumber.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {
	public LocalDateTime timestamp;
	public String details;
	
	public ExceptionResponse() {}

	public ExceptionResponse(LocalDateTime timestamp, String details) {
		super();
		this.timestamp = timestamp;
		this.details = details;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}	

}