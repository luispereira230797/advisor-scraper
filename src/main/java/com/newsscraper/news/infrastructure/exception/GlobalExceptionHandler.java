package com.newsscraper.news.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NewsValidationException.class)
	public ResponseEntity<ErrorDetails> handleUserValidationException(NewsValidationException exception) {
		var errorDetails = new ErrorDetails();
		errorDetails.setCode(exception.getCode());
		errorDetails.setError(exception.getMessage());
		return ResponseEntity.status(HttpStatus.valueOf(exception.getCodeError())).body(errorDetails);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleException(Exception exception) {
		exception.printStackTrace();
		var errorDetails = new ErrorDetails();
		errorDetails.setCode("g100");
		errorDetails.setError("Error interno del servidor");
		return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorDetails);
	}
}
