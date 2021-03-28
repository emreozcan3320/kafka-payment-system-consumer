package com.wefox.payment.api.service;

import com.wefox.payment.api.client.PaymentClient;
import com.wefox.payment.api.exception.DataBaseOperationException;
import com.wefox.payment.api.exception.InvalidPaymentException;
import com.wefox.payment.api.model.Account;
import com.wefox.payment.api.model.Payment;
import com.wefox.payment.api.repository.AccountRepository;
import com.wefox.payment.api.repository.PaymentRepository;
import com.wefox.payment.api.util.PaymentErrorLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final AccountRepository accountRepository;
	private final PaymentClient paymentClient;
	private final PaymentErrorLogger errorLogger;

	@Transactional
	public void processOfflinePayment(Payment payment) {
		try {
			paymentRepository.save(payment);
			accountRepository.save(getUpdatedAccount(payment.getAccount()));
		} catch(RuntimeException e) {
			errorLogger.log(payment.getPaymentId(), "Payment database save operation failed", new DataBaseOperationException());
		}
	}

	@Transactional
	public void processOnlinePayment(Payment payment) {
		if(paymentClient.isPaymentValid(payment)) {
			try {
				paymentRepository.save(payment);
				accountRepository.save(getUpdatedAccount(payment.getAccount()));
			} catch(RuntimeException e) {
				errorLogger.log(payment.getPaymentId(), "Payment database save operation failed", new DataBaseOperationException());
			}
		} else {
			errorLogger.log(payment.getPaymentId(), "Payment validation failed", new InvalidPaymentException());
		}
	}

	/*
	 * This method added because @UpdateTimestamp,@PrePersist or @Builder.DEFAULT doesnt worked
	 * If you found Solution remove this method
	 * */
	private Account getUpdatedAccount(Account account) {
		account.setLastPaymentDate(new Timestamp(System.currentTimeMillis()));
		return account;
	}
}
