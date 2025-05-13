package liptsoft.microservice.controller;

import liptsoft.microservice.model.Transaction;
import liptsoft.microservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController("/api")
@RequiredArgsConstructor
public class TransactionController {
	private final TransactionService transactionService;

	@GetMapping("/search")
	public ResponseEntity<List<Transaction>> search(
			@RequestParam Optional<Integer> from,
			@RequestParam Optional<Integer> to,
			@RequestParam Optional<LocalDateTime> startTime,
			@RequestParam Optional<LocalDateTime> endTime,
			@RequestParam Optional<Long> accountId,
			/*
			 Так как задание не предполагает аутентификации и авторизации,
			 будем получать id пользователя в параметрах запроса.
			 Я так понял, что пользователь может получить только принадлежащие ему транзакции
			*/
			@RequestParam Optional<Integer> customerId
	) {
		List<Transaction> transactions = transactionService.searchByFilters(from, to, startTime, endTime, accountId, customerId);
		return ResponseEntity.ok().body(transactions);
	}
	
	@GetMapping("/ping")
	public ResponseEntity<String> ping() {
		return ResponseEntity.ok("pong");
	}
}
