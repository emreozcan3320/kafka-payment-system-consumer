package com.wefox.payment.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "PAYMENTS")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("payment_id")
	private String paymentId;

	@JsonProperty("account_id")
	private Integer accountId;

	@Column(nullable = false)
	@JsonProperty("payment_type")
	private String paymentType;

	@Column(nullable = false)
	@JsonProperty("credit_card")
	private String creditCard;

	@Column(nullable = false)
	@JsonProperty("amount")
	private BigDecimal amount;

	@UpdateTimestamp
	private Timestamp createdOn;
}

