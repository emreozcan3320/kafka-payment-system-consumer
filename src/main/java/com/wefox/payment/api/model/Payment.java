package com.wefox.payment.api.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wefox.payment.api.enumeration.PaymentType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PAYMENTS")
public class Payment {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@JsonProperty("payment_id")
	private String paymentId;

	@Column(nullable = false)
	@JsonProperty("payment_type")
	private PaymentType paymentType;

	@Column(nullable = false)
	@JsonProperty("credit_card")
	private String creditCard;

	@Column(nullable = false)
	@JsonProperty("amount")
	private BigDecimal amount;

	@CreationTimestamp
	@Setter(AccessLevel.NONE)
	@JsonIgnore
	private Timestamp createdOn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	@JsonProperty("account_id")
	private Account account;

	@JsonGetter("account_id")
	public Integer getAccountId() {
		return account.getAccountId();
	}

	@JsonGetter("payment_type")
	public String getPaymentTypeAsString() {
		return paymentType.getName().toLowerCase();
	}

	@Builder
	public Payment(String paymentId, PaymentType paymentType, String creditCard, BigDecimal amount, Account account) {
		this.paymentId = paymentId;
		this.paymentType = paymentType;
		this.creditCard = creditCard;
		this.amount = amount;
		this.account = account;
	}
}
