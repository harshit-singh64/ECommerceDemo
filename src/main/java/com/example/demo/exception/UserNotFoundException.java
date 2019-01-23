package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotFoundException {
	private static final long serialVersionUID = 1L;
	
	private Integer code;
	private String message;
	
	public UserNotFoundException() {
		super();
	}
	public UserNotFoundException(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public UserNotFoundException(String message) {
		super();
		this.message = message;
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
	
	
}
