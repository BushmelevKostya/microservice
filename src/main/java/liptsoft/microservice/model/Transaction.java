package liptsoft.microservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transaction", indexes = {
		@Index(name = "idx_transaction_date_time", columnList = "date_time")
})
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
