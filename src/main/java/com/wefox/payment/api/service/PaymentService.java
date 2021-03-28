package com.wefox.payment.api.service;

import com.wefox.payment.api.client.PaymentClient;
import com.wefox.payment.api.exception.DataBaseOperationException;
import com.wefox.payment.api.exception.InvalidPaymentException;
import com.wefox.payment.api.exception.PaymentAccountNotFoundException;
import com.wefox.payment.api.exception.PaymentEmptyJsonValueException;
import com.wefox.payment.api.exception.PaymentIllegalJsonFormatException;
import com.wefox.payment.api.exception.PaymentMissingJsonKeyException;
import com.wefox.payment.api.factory.PaymentFactory;
import com.wefox.payment.api.model.Account;
import com.wefox.payment.api.model.Payment;
import com.wefox.payment.api.repository.AccountRepository;
import com.wefox.payment.api.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentFactory paymentFactory;
	private final PaymentRepository paymentRepository;
	private final AccountRepository accountRepository;
	private final PaymentClient paymentClient;

	@Transactional
	public void processOfflinePayment(String paymentJson) throws PaymentMissingJsonKeyException, PaymentIllegalJsonFormatException, PaymentAccountNotFoundException, PaymentEmptyJsonValueException, DataBaseOperationException {
		Payment payment = paymentFactory.getInstanceFromOfflinePayment(paymentJson);
		try {
			paymentRepository.save(payment);
			accountRepository.save(getUpdatedAccount(payment.getAccount()));
		} catch(RuntimeException e) {
			throw new DataBaseOperationException(payment.getPaymentId() + " id payment can not saved to database", e);
		}
	}

	@Transactional
	public void processOnlinePayment(String paymentJson) throws PaymentMissingJsonKeyException, PaymentIllegalJsonFormatException, PaymentAccountNotFoundException, PaymentEmptyJsonValueException, DataBaseOperationException, InvalidPaymentException {
		Payment payment = paymentFactory.getInstanceFromOnlinePayment(paymentJson);
		if(paymentClient.isPaymentValid(payment)) {
			try {
				paymentRepository.save(payment);
				accountRepository.save(getUpdatedAccount(payment.getAccount()));
			} catch(RuntimeException e) {
				throw new DataBaseOperationException(payment.getPaymentId() + " id payment can not saved to database", e);
			}
		} else {
			throw new InvalidPaymentException("Payment validation failed");
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
