package com.wefox.payment.api.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;


@Data
@Entity
@Table(name = "ACCOUNTS")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long accountId;
	@Column(nullable = false)
	private String email;
	private Date birthdate;
	private Timestamp lastPaymentDate;
	@CreationTimestamp
	private Timestamp createdOn;


}

