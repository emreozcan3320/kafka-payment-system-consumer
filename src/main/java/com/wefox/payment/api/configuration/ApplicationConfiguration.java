package com.wefox.payment.api.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApplicationConfiguration {
	private final Environment environment;

	public String getValidationUrl() {
		return Optional.ofNullable(environment.getProperty("custom.url.validation"))
				.orElseThrow(() -> new RuntimeException("Validation URL is required"));
	}

	public String getLoggingUrl() {
		return Optional.ofNullable(environment.getProperty("custom.url.log"))
				.orElseThrow(() -> new RuntimeException("Logging URL is required"));
	}
}
