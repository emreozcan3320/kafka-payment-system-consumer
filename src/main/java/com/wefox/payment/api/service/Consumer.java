package com.wefox.payment.api.service;

import com.wefox.payment.api.exception.DataBaseOperationException;
import com.wefox.payment.api.exception.InvalidPaymentException;
import com.wefox.payment.api.exception.PaymentAccountNotFoundException;
import com.wefox.payment.api.exception.PaymentEmptyJsonValueException;
import com.wefox.payment.api.exception.PaymentIllegalJsonFormatException;
import com.wefox.payment.api.exception.PaymentMissingJsonKeyException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Consumer {
	private final Logger logger = LoggerFactory.getLogger(Consumer.class);

	private final PaymentService paymentService;


	@KafkaListener(topics = "${custom.kafka.consumer.topic.offline.name}")
	public void consumeOfflinePayment(String paymentJson) {
		try {
			paymentService.processOfflinePayment(paymentJson);
		} catch(PaymentMissingJsonKeyException e) {
			e.printStackTrace();
		} catch(PaymentIllegalJsonFormatException e) {
			e.printStackTrace();
		} catch(PaymentAccountNotFoundException e) {
			e.printStackTrace();
		} catch(PaymentEmptyJsonValueException e) {
			e.printStackTrace();
		} catch(DataBaseOperationException e) {
			e.printStackTrace();
		}
	}

	@KafkaListener(topics = "${custom.kafka.consumer.topic.online.name}")
	public void consumeOnlinePayment(String paymentJson) {
		try {
			paymentService.processOnlinePayment(paymentJson);
		} catch(PaymentMissingJsonKeyException e) {
			e.printStackTrace();
		} catch(PaymentIllegalJsonFormatException e) {
			e.printStackTrace();
		} catch(PaymentAccountNotFoundException e) {
			e.printStackTrace();
		} catch(PaymentEmptyJsonValueException e) {
			e.printStackTrace();
		} catch(DataBaseOperationException e) {
			e.printStackTrace();
		} catch(InvalidPaymentException e) {
			e.printStackTrace();
		}
	}

}
