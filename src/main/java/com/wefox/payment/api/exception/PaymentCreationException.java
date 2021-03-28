package com.wefox.payment.api.exception;

public class PaymentCreationException extends RuntimeException {

	public PaymentCreationException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
