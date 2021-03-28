package com.wefox.payment.api.util;

import com.wefox.payment.api.client.LogClient;
import com.wefox.payment.api.model.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

/*
 * Normally, I use Logstash and ELK(Elasticsearch, Logstash, and Kibana)
 * like approach but due to project requirement
 * I created this PaymentErrorLogger Util
 * */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentErrorLogger {

	private final LogClient logClient;

	public void log(String paymentId, String message, Exception e) {
		log.error("PaymentId: {} ErrorType: {} ErrorDescription: {}", paymentId, e.getClass().toString(), message);
		Log paymentErrorLog = Log
				.builder()
				.paymentId(paymentId)
				.errorType(e.getClass().toString())
				.errorDescription("message: " + message + "\nStackTrace: " + stackTraceStringify(e))
				.build();
		logClient.saveErrorLog(paymentErrorLog);
	}

	private String stackTraceStringify(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
}
