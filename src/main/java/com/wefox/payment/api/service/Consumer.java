package com.wefox.payment.api.service;

import com.wefox.payment.api.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
	private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

	@KafkaListener(topics = "online")
	public void consume(Payment payment) {
		logger.info(String.format("** Consumer received the online payment -> %s **", payment));
	}
}
