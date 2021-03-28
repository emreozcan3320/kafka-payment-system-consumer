package com.wefox.payment.api.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefox.payment.api.exception.PaymentAccountNotFoundException;
import com.wefox.payment.api.exception.PaymentEmptyJsonValueException;
import com.wefox.payment.api.exception.PaymentIllegalJsonFormatException;
import com.wefox.payment.api.exception.PaymentMissingJsonKeyException;
import com.wefox.payment.api.model.Account;
import com.wefox.payment.api.model.Payment;
import com.wefox.payment.api.repository.AccountRepository;
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

	public Payment getInstanceFromOfflinePayment(String paymentJson) throws PaymentIllegalJsonFormatException, PaymentEmptyJsonValueException, PaymentMissingJsonKeyException, PaymentAccountNotFoundException {
		JsonNode jsonNode = getJsonNodeFromJson(paymentJson);
		validatePaymentJson(jsonNode, required_offline_payment_keys);
		return getPaymentFromJsonNode(jsonNode);
	}

	public Payment getInstanceFromOnlinePayment(String paymentJson) throws PaymentIllegalJsonFormatException, PaymentEmptyJsonValueException, PaymentMissingJsonKeyException, PaymentAccountNotFoundException {
		JsonNode jsonNode = getJsonNodeFromJson(paymentJson);
		validatePaymentJson(jsonNode, required_online_payment_keys);
		return getPaymentFromJsonNode(jsonNode);
	}

	private JsonNode getJsonNodeFromJson(String paymentJson) throws PaymentIllegalJsonFormatException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readTree(paymentJson);
		} catch(JsonProcessingException e) {
			throw new PaymentIllegalJsonFormatException(
					"Json input" + paymentJson + " format doesn't match " +
							"{\"payment_id\": \"[payment_id]\", \"account_id\": [account_id], \"payment_type\": \"[payment_type]\"," +
							" \"credit_card\": \"[credit_card]\", \"amount\": [amount], \"delay\": [delay]}"
					, e
			);
		}
	}

	private Payment getPaymentFromJsonNode(JsonNode jsonNode) throws PaymentAccountNotFoundException {
		Account account = findAccountByAccountIdFromPaymentJson(jsonNode.get("account_id").asText());
		return Payment
				.builder()
				.paymentId(jsonNode.get("payment_id").asText())
				.paymentType(jsonNode.get("payment_type").asText())
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
