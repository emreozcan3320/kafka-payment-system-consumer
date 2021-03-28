package com.wefox.payment.api.exception;

public class DataBaseOperationException extends RuntimeException{

	public DataBaseOperationException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
