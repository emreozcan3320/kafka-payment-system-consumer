package com.wefox.payment.api.client;

import com.wefox.payment.api.configuration.ApplicationConfiguration;
import com.wefox.payment.api.model.Payment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentClient extends ClientAdapter {

	private final ApplicationConfiguration applicationConfiguration;

	public PaymentClient(RestTemplate restTemplate, ApplicationConfiguration applicationConfiguration) {
		super(restTemplate);
		this.applicationConfiguration = applicationConfiguration;
	}

	/*
	 * if Payment valid, Payment Api return 200
	 * I dont know what it returns, if it is not valid
	 * therefore I have to return false for every exception
	 * */
	public boolean isPaymentValid(Payment payment) {
		try {
			post(applicationConfiguration.getValidationUrl(), Payment.class, payment);
			return true;
		} catch(RuntimeException e) {
			return false;
		}
	}
}
