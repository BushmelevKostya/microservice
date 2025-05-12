package liptsoft.microservice.service;

import liptsoft.microservice.model.Transaction;
import liptsoft.microservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
	/*
	 * Поскольку файл не помещается целиком в оперативную память, будем работать с базой данных для эффективной
	 * выборки записей. Так как необходимо сортировать и фильтровать по дате, необходимо сделать индекс на этот атрибут
	 */
	private final TransactionRepository transactionRepository;
	
	private final int defaultFromValue = 1;
	private final int defaultToValue = 20;
	private final LocalDateTime defaultStartDateValue = LocalDateTime.parse("1990-01-01T00:00:00");
	private final LocalDateTime defaultEndDateValue = LocalDateTime.now();
	
	
	public List<Transaction> searchByFilters(
			Optional<Integer> from,
			Optional<Integer> to,
			Optional<LocalDateTime> startTime,
			Optional<LocalDateTime> endTime,
			Optional<Long> accountId,
			Integer customerId
	) {
		int fromInt = from.orElse(defaultFromValue) - 1; // -1 для offset
		int toInt = to.orElse(defaultToValue);
		int limit = toInt - fromInt + 1; // +1 для limit
		LocalDateTime startDateTime = startTime.orElse(defaultStartDateValue);
		LocalDateTime endDateTime = endTime.orElse(defaultEndDateValue);
		Long account = accountId.orElse(null);
		
		return transactionRepository.findByFilters(
				fromInt, limit, startDateTime, endDateTime, account, customerId
		);
	}
}
