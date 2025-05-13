package liptsoft.microservice.service;

import liptsoft.microservice.model.Transaction;
import liptsoft.microservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
	private final TransactionRepository transactionRepository;
	
	private final int defaultFromValue = 1;
	private final int defaultToValue = Integer.MAX_VALUE - 1;
	private final LocalDateTime defaultStartDateValue = LocalDateTime.parse("1990-01-01T00:00:00");
	private final LocalDateTime defaultEndDateValue = LocalDateTime.now();
	
	
	public List<Transaction> searchByFilters(
			Optional<Integer> from,
			Optional<Integer> to,
			Optional<LocalDateTime> startTime,
			Optional<LocalDateTime> endTime,
			Optional<Long> accountId,
			Optional<Integer> customerId
	) {
		if (customerId.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "должен быть задан customerId");
		
		int fromInt = from.orElse(defaultFromValue) - 1; // -1 для offset
		int toInt = to.orElse(defaultToValue);
		
		if (fromInt < 0 || toInt < 1) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "`from` и `to` должны быть ≥ 1");
		if (fromInt > toInt) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "`from` должен быть ≤ `to`");
		
		int limit = toInt - fromInt;
		LocalDateTime startDateTime = startTime.orElse(defaultStartDateValue);
		LocalDateTime endDateTime = endTime.orElse(defaultEndDateValue);
		
		if (startTime.isPresent() && endTime.isPresent() && startDateTime.isAfter(endDateTime)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "`startTime` должен быть до `endTime`");
		
		Long account = accountId.orElse(null);

		return transactionRepository.findByFilters(
				fromInt, limit, startDateTime, endDateTime, account, customerId.get()
		);
	}
}
