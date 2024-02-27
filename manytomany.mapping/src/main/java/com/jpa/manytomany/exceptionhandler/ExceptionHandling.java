package com.jpa.manytomany.exceptionhandler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.jpa.manytomany.response.ResponseError;

@ControllerAdvice
public class ExceptionHandling {
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ResponseError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ResponseError(" Incorrect data type! Please enter numeric value" ));
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ResponseError> handleHttpMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError(" Please check the method."));
	}
	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ResponseError> handleNoResourceFoundException(NoResourceFoundException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("You have entered incorrect Url!"));
	}
	
//	@ExceptionHandler(DataIntegrityViolationException.class)
//	public ResponseEntity<ResponseError> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
//		return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Duplicate Employee Id!" ));
//	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ResponseError> handleNullPointerException(NullPointerException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Please enter all the fileds."));
	}
}