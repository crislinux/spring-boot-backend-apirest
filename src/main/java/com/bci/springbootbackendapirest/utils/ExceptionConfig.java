package com.bci.springbootbackendapirest.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice (annotations = RestController.class)
public class ExceptionConfig {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> notFoundException(NotFoundException e) {	 
		return buildResponseEntity(HttpStatus.NOT_FOUND, e, null);		
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> badRequestException(BadRequestException e) {
		return buildResponseEntity(HttpStatus.BAD_REQUEST, e, null);
	}
	
	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<ErrorResponse> duplicateKeyException(DuplicateKeyException e) {
		return buildResponseEntity(HttpStatus.BAD_REQUEST, e, null);	
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> illegalArgumentException(IllegalArgumentException e) {
		return buildResponseEntity(HttpStatus.BAD_REQUEST, e, null);		
	}
	
	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<ErrorResponse> invalidDataException(InvalidDataException e) {
		 List<String> errors = e.getResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
			        .collect(Collectors.toList());
			    return buildResponseEntity(HttpStatus.BAD_REQUEST, e, errors);	
	}
	
	private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus httpStatus, Exception exc, List<String> errors) {
	    ErrorResponse error = new ErrorResponse(exc.getMessage());
	    if (errors != null && !errors.isEmpty()) {
	        String errorMessage = String.join(System.lineSeparator(), errors);
	        error.setMessage(" " + errorMessage);
	    }
	    return new ResponseEntity<>(error, httpStatus);
	}

}
