package com.wefox.payment.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Builder
@NoArgsConstructor
@Entity
@Table(name = "ACCOUNTS")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer accountId;

	@Column(nullable = false)
	private String email;

	private Date birthdate;

	/*
	 * @UpdateTimestamp,@PrePersist or @Builder.DEFAULT doesnt work
	 * I cant find the reason so I have to set manually
	 * */
	private Timestamp lastPaymentDate;

	@CreationTimestamp
	private Timestamp createdOn;

}
