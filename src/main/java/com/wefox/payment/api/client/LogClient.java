package com.wefox.payment.api.client;

import com.wefox.payment.api.configuration.ApplicationConfiguration;
import com.wefox.payment.api.model.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LogClient extends ClientAdapter {

	private final ApplicationConfiguration applicationConfiguration;

	public LogClient(RestTemplate restTemplate, ApplicationConfiguration applicationConfiguration) {
		super(restTemplate);
		this.applicationConfiguration = applicationConfiguration;
	}

	public boolean saveErrorLog(Log log) {
		try {
			post(applicationConfiguration.getLoggingUrl(), Log.class, log);
			return true;
		} catch(RuntimeException e) {
			return false;
		}
	}
}
