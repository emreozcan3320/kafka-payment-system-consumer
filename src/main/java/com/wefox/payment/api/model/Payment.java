package com.wefox.payment.api.model;

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
	private Long paymentId;
	private Integer accountId;
	@Column(nullable = false)
	private String paymentType;
	@Column(nullable = false)
	private String creditCard;
	@Column(nullable = false)
	private BigDecimal amount;
	@UpdateTimestamp
	private Timestamp createdOn;

}

