package liptsoft.microservice.service;

import liptsoft.microservice.model.Transaction;
import liptsoft.microservice.repository.TransactionRepository;
import liptsoft.microservice.util.CsvTransactionReader;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
	/*
	 * Поскольку файл не помещается целиком в оперативную память, будем работать с базой данных для эффективной
	 * выборки записей. Так как необходимо сортировать и фильтровать по дате, необходимо сделать индекс на этот атрибут
	 */
	TransactionRepository transactionRepository;
	
	public List<Transaction> searchByFilters(
			Optional<Integer> from,
			Optional<Integer> to,
			Optional<LocalDateTime> startTime,
			Optional<LocalDate> endTime,
			Optional<Long> accountId,
			Integer customerId
	) {
		ArrayList<Transaction> transactions = new ArrayList<>();
		
		
		
		return transactions;
	}
}
