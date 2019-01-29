package com.example.demo.exception;

public class CustomException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private Integer code;
	private String message;
	private String errorMessage;
	
	public CustomException() {
		super();
	}
	public CustomException(String message) {
		super();
		this.message = message;
	}
	public CustomException(String message, String errorMessage) {
		super();
		this.message = message;
		this.errorMessage = errorMessage;
	}
	public CustomException(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public CustomException(Integer code, String message, String errorMessage) {
		super();
		this.code = code;
		this.message = message;
		this.errorMessage = errorMessage;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
