package com.bci.springbootbackendapirest.utils;

public class NotFoundException extends RuntimeException{
	
	public NotFoundException() {
		
	}
	
	public NotFoundException(String message) {
		super(message);
	}

}
