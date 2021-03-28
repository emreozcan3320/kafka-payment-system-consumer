package com.wefox.payment.api.exception;

public class PaymentIllegalJsonFormatException extends RuntimeException {

	public PaymentIllegalJsonFormatException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
