package liptsoft.microservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Transaction {
	@Id
	private long transactionId;
	private int customerId;
	private long accountId;
	private BigDecimal amount;
	private LocalDateTime dateTime;
	@Enumerated(EnumType.STRING)
	private Type type;
}
