package com.wefox.payment.api.enumeration;

import com.wefox.payment.api.exception.PaymentIllegalPaymentTypeException;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum PaymentType {
	OFFLINE("offline"),
	ONLINE("online");

	private String name;
	private static final Map<String, PaymentType> ENUM_MAP;

	PaymentType(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	/*
	 * I can use a foreach and if name match, I could return Payment type or throw exception
	 * BUT its time complexity O(n) linear time this solution more verbose but creating a immutable Hashmap
	 * decrease time complexity O(1) constant time.
	 * */
	static {
		Map<String, PaymentType> map = new ConcurrentHashMap<String, PaymentType>();
		for(PaymentType instance : PaymentType.values()) {
			map.put(instance.getName().toLowerCase(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	public static PaymentType get(String name) throws PaymentIllegalPaymentTypeException {
		PaymentType paymentType = ENUM_MAP.get(name.toLowerCase());
		if(paymentType == null) {
			throw new PaymentIllegalPaymentTypeException("No constant with text " + name + " found");
		} else {
			return paymentType;
		}
	}
}
