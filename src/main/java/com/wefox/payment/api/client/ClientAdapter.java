package com.wefox.payment.api.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public abstract class ClientAdapter {

	private final RestTemplate restTemplate;

	private <T> HttpEntity<T> generateHttpEntity(T data) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return new HttpEntity<>(data, httpHeaders);
	}

	protected <T> T get(String fullUrl, Class<T> model) throws HttpStatusCodeException {
		try {
			return restTemplate.exchange(fullUrl, HttpMethod.GET, generateHttpEntity(null), model).getBody();
		} catch(HttpStatusCodeException e) {
			throw e;
		}
	}

	protected <T> T post(String fullUrl, Class<T> model, T data) throws HttpStatusCodeException {
		try {
			return restTemplate.postForEntity(fullUrl, generateHttpEntity(data), model).getBody();
		} catch(HttpStatusCodeException e) {
			throw e;
		}
	}
}
