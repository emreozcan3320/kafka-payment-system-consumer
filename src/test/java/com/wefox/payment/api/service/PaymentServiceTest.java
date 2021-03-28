package com.wefox.payment.api.service;

import com.wefox.payment.api.client.PaymentClient;
import com.wefox.payment.api.enumeration.PaymentType;
import com.wefox.payment.api.model.Account;
import com.wefox.payment.api.model.Payment;
import com.wefox.payment.api.repository.AccountRepository;
import com.wefox.payment.api.repository.PaymentRepository;
import com.wefox.payment.api.util.PaymentErrorLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PaymentServiceTest {

	private final PaymentRepository paymentRepository = mock(PaymentRepository.class);
	private final AccountRepository accountRepository = mock(AccountRepository.class);
	private final PaymentClient paymentClient = mock(PaymentClient.class);
	private final PaymentErrorLogger errorLogger = mock(PaymentErrorLogger.class);
	private final PaymentService paymentService = new PaymentService(paymentRepository, accountRepository, paymentClient, errorLogger);
	private Payment payment;

	@BeforeEach
	void before() {
		payment = Payment
				.builder()
				.paymentId("1")
				.paymentType(PaymentType.OFFLINE)
				.amount(new BigDecimal(10))
				.creditCard("123")
				.account(
						Account
								.builder()
								.accountId(1)
								.build()
				)
				.build();
	}

	@Test
	void processOfflinePayment() {
		paymentService.processOfflinePayment(payment);
		verify(paymentRepository).save(any());
		verify(accountRepository).save(any());
	}

	@Test
	void processOnlinePayment() {
		paymentService.processOnlinePayment(payment);
		verify(paymentRepository).save(any());
		verify(accountRepository).save(any());
	}
}
