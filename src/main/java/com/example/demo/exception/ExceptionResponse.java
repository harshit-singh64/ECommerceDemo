package com.example.demo.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ExceptionResponse {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private HttpStatus status;
	private Integer errorCode;
	private String error;
	private String errorMessage;
	private String requestedURI;
	private List<String> errors;
	
	public ExceptionResponse() {
		super();
		timestamp = LocalDateTime.now();
	}

	public ExceptionResponse(LocalDateTime timestamp, HttpStatus status, Integer errorCode, String error, String errorMessage,
			String requestedURI) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.errorCode = errorCode;
		this.error = error;
		this.errorMessage = errorMessage;
		this.requestedURI = requestedURI;
	}
	
	/*public ExceptionResponse(LocalDateTime timestamp, HttpStatus status, Integer errorCode, String error, String errorMessage,
			String requestedURI) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.errorMessage = errorMessage;
		this.requestedURI = requestedURI;
	}*/
	
	public ExceptionResponse(LocalDateTime timestamp, String errorMessage, String requestedURI) {
		super();
		this.timestamp = timestamp;
		this.errorMessage = errorMessage;
		this.requestedURI = requestedURI;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getRequestedURI() {
		return requestedURI;
	}

	public void setRequestedURI(String requestedURI) {
		this.requestedURI = requestedURI;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	/*dont remove below this*/
	public void callerURL(final String requestedURI) {
		this.requestedURI = requestedURI;
	}
	
}
