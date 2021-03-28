package com.wefox.payment.api.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.wefox.payment.api.enumeration.PaymentType;
import com.wefox.payment.api.exception.PaymentAccountNotFoundException;
import com.wefox.payment.api.exception.PaymentCreationException;
import com.wefox.payment.api.exception.PaymentEmptyJsonValueException;
import com.wefox.payment.api.exception.PaymentIllegalPaymentTypeException;
import com.wefox.payment.api.exception.PaymentMissingJsonKeyException;
import com.wefox.payment.api.model.Account;
import com.wefox.payment.api.model.Payment;
import com.wefox.payment.api.repository.AccountRepository;
import com.wefox.payment.api.util.PaymentErrorLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentFactory {

	private final AccountRepository accountRepository;
	private final List<String> required_offline_payment_keys = Arrays.asList("payment_id", "account_id", "payment_type", "amount");
	private final List<String> required_online_payment_keys = Arrays.asList("payment_id", "account_id", "payment_type", "credit_card", "amount");
	private final PaymentErrorLogger errorLogger;

	public Payment getInstanceFromOfflinePayment(JsonNode paymentJsonNode) {
		try {
			validatePaymentJson(paymentJsonNode, required_offline_payment_keys);
			return getPaymentFromJsonNode(paymentJsonNode);
		} catch(Exception e) {
			errorLogger.log(paymentJsonNode.get("payment_id").asText(), e.getMessage(), e);
			throw new PaymentCreationException("Payment instance can not created", e);
		}
	}

	public Payment getInstanceFromOnlinePayment(JsonNode paymentJsonNode) {
		try {
			validatePaymentJson(paymentJsonNode, required_online_payment_keys);
			return getPaymentFromJsonNode(paymentJsonNode);
		} catch(Exception e) {
			errorLogger.log(paymentJsonNode.get("payment_id").asText(), e.getMessage(), e);
			throw new PaymentCreationException("Payment instance can not created", e);
		}
	}

	private Payment getPaymentFromJsonNode(JsonNode jsonNode) throws PaymentAccountNotFoundException, PaymentIllegalPaymentTypeException {
		Account account = findAccountByAccountIdFromPaymentJson(jsonNode.get("account_id").asText());
		return Payment
				.builder()
				.paymentId(jsonNode.get("payment_id").asText())
				.paymentType(PaymentType.get(jsonNode.get("payment_type").asText()))
				.creditCard(jsonNode.get("credit_card").asText())
				.amount(new BigDecimal(jsonNode.get("amount").asText()))
				.account(account)
				.build();
	}

	private void validatePaymentJson(JsonNode jsonNode, List<String> keys) throws PaymentMissingJsonKeyException, PaymentEmptyJsonValueException {
		for(String key : keys) {
			if(jsonNode.get(key) == null) {
				throw new PaymentMissingJsonKeyException(key + " Key not found");
			} else if(jsonNode.get(key).asText().equals("")) {
				throw new PaymentEmptyJsonValueException(key + " key value is empty, value can not be empty");
			}
		}
	}

	private Account findAccountByAccountIdFromPaymentJson(String accountId) throws PaymentAccountNotFoundException {
		Optional<Account> account = accountRepository.findById(Integer.parseInt(accountId));
		if(account.isPresent()) {
			return account.get();
		} else {
			throw new PaymentAccountNotFoundException("Payment Account with " + accountId + " id not found");
		}
	}
}
