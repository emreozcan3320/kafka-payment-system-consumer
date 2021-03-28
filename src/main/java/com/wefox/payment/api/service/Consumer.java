package com.wefox.payment.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefox.payment.api.exception.PaymentIllegalJsonFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Consumer {

	private final PaymentService paymentService;

	@KafkaListener(topics = "${custom.kafka.consumer.topic.offline.name}")
	public void consumeOfflinePayment(String paymentJson) {
		JsonNode paymentJsonNode = getJsonNodeFromJson(paymentJson);
		paymentService.processOfflinePayment(paymentJsonNode);
	}

	@KafkaListener(topics = "${custom.kafka.consumer.topic.online.name}")
	public void consumeOnlinePayment(String paymentJson) {
		JsonNode paymentJsonNode = getJsonNodeFromJson(paymentJson);
		paymentService.processOnlinePayment(paymentJsonNode);
	}

	private JsonNode getJsonNodeFromJson(String paymentJson) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readTree(paymentJson);
		} catch(JsonProcessingException e) {
			throw new PaymentIllegalJsonFormatException(
					"Json input" + paymentJson + " format doesn't match " +
							"{\"payment_id\": \"[payment_id]\", \"account_id\": [account_id], \"payment_type\": \"[payment_type]\"," +
							" \"credit_card\": \"[credit_card]\", \"amount\": [amount], \"delay\": [delay]}"
					, e
			);
		}
	}
}
