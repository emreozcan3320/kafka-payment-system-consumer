package com.wefox.payment.api.service;

import com.wefox.payment.api.exception.DataBaseOperationException;
import com.wefox.payment.api.exception.PaymentAccountNotFoundException;
import com.wefox.payment.api.exception.PaymentEmptyJsonValueException;
import com.wefox.payment.api.exception.PaymentIllegalJsonFormatException;
import com.wefox.payment.api.exception.PaymentMissingJsonKeyException;
import com.wefox.payment.api.factory.PaymentFactory;
import com.wefox.payment.api.model.Payment;
import com.wefox.payment.api.repository.AccountRepository;
import com.wefox.payment.api.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentFactory paymentFactory;
	private final PaymentRepository paymentRepository;
	private final AccountRepository accountRepository;

	@Transactional
	public void processOfflinePayment(String paymentJson) throws PaymentMissingJsonKeyException, PaymentIllegalJsonFormatException, PaymentAccountNotFoundException, PaymentEmptyJsonValueException, DataBaseOperationException {
		Payment payment = paymentFactory.getInstanceFromOfflinePayment(paymentJson);
		try {
			paymentRepository.save(payment);
			accountRepository.save(payment.getAccount());
		} catch(RuntimeException e) {
			throw new DataBaseOperationException(payment.getPaymentId() + " id payment can not saved to database", e);
		}
	}
}
