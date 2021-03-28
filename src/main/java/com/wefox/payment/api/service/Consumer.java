package com.wefox.payment.api.service;

import com.wefox.payment.api.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
	private final Logger logger = LoggerFactory.getLogger(Consumer.class);

	@KafkaListener(topics = "${custom.kafka.consumer.topic.offline.name}")
	public void consumeOfflinePayment(Payment payment) {
		logger.info(String.format("** Consumer received the offline payment -> %s **", payment));
	}

	@KafkaListener(topics = "${custom.kafka.consumer.topic.online.name}")
	public void consumeOnlinePayment(Payment payment) {
		logger.info(String.format("** Consumer received the online payment -> %s **", payment));
	}


}
