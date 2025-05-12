package liptsoft.microservice.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter

public class Transaction {
	private long transactionId;
	private int customerId;
	private long accountId;
	private BigDecimal amount;
	private LocalDateTime dateTime;
	private Type type;
}
