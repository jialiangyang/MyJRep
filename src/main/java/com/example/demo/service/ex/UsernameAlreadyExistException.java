package com.example.demo.service.ex;

public class UsernameAlreadyExistException extends RuntimeException {

	public UsernameAlreadyExistException() {
	}

	public UsernameAlreadyExistException(String message) {
		super(message);
	}

	
}
