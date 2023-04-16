package com.bosh.challenger.exceptions;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler{

	// @ExceptionHandler(value = {InvalidValueException.class})
	 protected ResponseEntity<Object> handle(
			 InvalidValueException ex, WebRequest request) {
		      
		        Map<String, Object> body = new LinkedHashMap<>();
		        body.put("timestamp", LocalDateTime.now());
		        body.put("message", ex.getMessage());
		        
		        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
		        
		    }

	
	
}
