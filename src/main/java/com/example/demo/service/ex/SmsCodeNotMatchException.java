package com.example.demo.service.ex;

public class SmsCodeNotMatchException extends RuntimeException {

	public SmsCodeNotMatchException() {
	}

	public SmsCodeNotMatchException(String message) {
		super(message);
	}

	
}
