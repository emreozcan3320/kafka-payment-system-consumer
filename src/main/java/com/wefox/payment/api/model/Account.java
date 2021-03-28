package com.wefox.payment.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;


@Getter
@Setter
@Entity
@Table(name = "ACCOUNTS")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer accountId;

	@Column(nullable = false)
	private String email;

	private Date birthdate;

	@Builder.Default
	private Timestamp lastPaymentDate = new Timestamp(System.currentTimeMillis());

	@CreationTimestamp
	private Timestamp createdOn;
}
