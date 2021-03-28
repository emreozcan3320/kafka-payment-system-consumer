package com.wefox.payment.api.exception;

public class PaymentIllegalJsonFormatException extends Exception {

	public PaymentIllegalJsonFormatException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
