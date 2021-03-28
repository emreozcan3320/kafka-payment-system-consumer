package com.wefox.payment.api.factory;

import com.wefox.payment.api.repository.AccountRepository;
import com.wefox.payment.api.util.PaymentErrorLogger;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/*
* This is a test example to show what can be done.
* Normally, writing a test for Factory class is controversial topic
* Because I should only care whether factory return a instance or not
* but here I am testing inner mechanism so Factory class testing is
* more integration testing than unit testing.
* */
class PaymentFactoryTest {

	private final AccountRepository accountRepository = mock(AccountRepository.class);
	private final PaymentErrorLogger errorLogger = mock(PaymentErrorLogger.class);
	private final PaymentFactory paymentFactory = new PaymentFactory(accountRepository, errorLogger);

	@Test
	void getInstanceFromOfflinePayment() {
	}

	@Test
	void getInstanceFromOnlinePayment() {
	}

	@Test
	void shouldThrowPaymentMissingJsonKeyException() {
	}

	@Test
	void shouldThrowPaymentEmptyJsonValueException(){
	}

	@Test
	void shouldThrowPaymentAccountNotFoundException(){
	}

	@Test
	void shouldThrowPaymentIllegalPaymentTypeException(){
	}
}
