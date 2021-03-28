package com.wefox.payment.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {

	@JsonProperty("payment_id")
	private String paymentId;

	@JsonProperty("error_type")
	private String errorType;

	@JsonProperty("error_description")
	private String errorDescription;
}
