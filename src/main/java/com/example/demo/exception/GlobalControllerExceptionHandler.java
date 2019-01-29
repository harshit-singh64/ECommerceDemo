package com.example.demo.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.util.ValidationUtil;

@ControllerAdvice
@RestController
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(InvalidInputException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public final ResponseEntity<Object> handleInvalidInputException(InvalidInputException exception, 
			WebRequest request) {
		
		ExceptionResponse error = new ExceptionResponse( LocalDateTime.now(), HttpStatus.valueOf(exception.getCode()),
				exception.getCode(),"Validation Failed", "invalid input Exception", exception.getMessage()); 
		
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
		  }
	
	@ExceptionHandler(CustomException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public final ResponseEntity<Object> handleInvalidInputException(CustomException exception, 
			WebRequest request) {
		
		ExceptionResponse error = new ExceptionResponse( LocalDateTime.now(), HttpStatus.valueOf(exception.getCode()),
				exception.getCode(),"Validation Failed", exception.getMessage(), exception.toString()+" >> "+exception.getErrorMessage()); 
		
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
		  }
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleAllException(final Exception exception, 
			final HttpServletRequest request) {
		Integer statusCode = 500;
		
		BindingResult result = ((MethodArgumentNotValidException) exception).getBindingResult();
		ExceptionResponse error = new ExceptionResponse();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(HttpStatus.valueOf(statusCode));
		error.setErrorCode(statusCode);
		error.setError("Validation Failed");
		error.setErrorMessage(exception.getMessage());
		error.callerURL(request.getRequestURI());
		error.setErrors(ValidationUtil.fromBindingErrors(result));
		
		return error;
	}
	
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Integer statusCode = 400;
		BindingResult result = exception.getBindingResult();
		// (this is the error url ->) exception.getBindingResult().toString()
		
		ExceptionResponse error = new ExceptionResponse(LocalDateTime.now(), HttpStatus.valueOf(statusCode), 
				statusCode,"Validation Failed","Invalid Input","null values are not allowed"); 
		error.setErrors(ValidationUtil.fromBindingErrors(result));
		
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
		  }
}
