package com.wefox.payment.api.model;

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
	private String paymentId;

	@Column(nullable = false)
	private String paymentType;

	@Column(nullable = false)
	private String creditCard;

	@Column(nullable = false)
	private BigDecimal amount;

	@CreationTimestamp
	@Setter(AccessLevel.NONE)
	private Timestamp createdOn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@Builder
	public Payment(String paymentId, String paymentType, String creditCard, BigDecimal amount, Account account) {
		this.paymentId = paymentId;
		this.paymentType = paymentType;
		this.creditCard = creditCard;
		this.amount = amount;
		this.account = account;
	}
}
