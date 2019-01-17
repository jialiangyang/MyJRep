package com.example.demo.service.ex;

public class PasswordNotMatchException extends RuntimeException {

	public PasswordNotMatchException() {
	}

	public PasswordNotMatchException(String message) {
		super(message);
	}

	
}
