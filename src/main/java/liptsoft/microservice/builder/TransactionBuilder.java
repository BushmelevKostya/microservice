package liptsoft.microservice.builder;

import liptsoft.microservice.model.Transaction;
import liptsoft.microservice.model.Type;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionBuilder {
	private final Transaction tx = new Transaction();
	
	public TransactionBuilder setTransactionId(long id) {
		tx.setTransactionId(id);
		return this;
	}
	
	public TransactionBuilder setCustomerId(int id) {
		tx.setCustomerId(id);
		return this;
	}
	
	public TransactionBuilder setAccountId(long id) {
		tx.setAccountId(id);
		return this;
	}
	
	public TransactionBuilder setAmount(BigDecimal amt) {
		tx.setAmount(amt);
		return this;
	}
	
	public TransactionBuilder setDateTime(LocalDateTime dt) {
		tx.setDateTime(dt);
		return this;
	}
	
	public TransactionBuilder setType(Type type) {
		tx.setType(type);
		return this;
	}
	
	public Transaction build() {
		return tx;
	}
}
