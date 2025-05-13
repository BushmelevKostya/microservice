package liptsoft.microservice;

import liptsoft.microservice.model.Transaction;
import liptsoft.microservice.repository.TransactionRepository;
import liptsoft.microservice.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {
	
	private TransactionRepository repository;
	private TransactionService service;
	
	@BeforeEach
	void setUp() {
		repository = mock(TransactionRepository.class);
		service = new TransactionService(repository);
	}
	
	@Test
	void returnDefaultTransactions() {
		List<Transaction> mockResult = List.of(createTransaction(1L));
		when(repository.findByFilters(anyInt(), anyInt(), any(), any(), any(), eq(1)))
				.thenReturn(mockResult);
		
		List<Transaction> result = service.searchByFilters(
				Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.of(1));
		
		assertEquals(1, result.size());
		verify(repository, times(1)).findByFilters(anyInt(), anyInt(), any(), any(), any(), eq(1));
	}
	
	@Test
	void returnSubsetWithPagination() {
		List<Transaction> mockResult = List.of(createTransaction(1L), createTransaction(2L));
		when(repository.findByFilters(eq(0), eq(2), any(), any(), any(), eq(1)))
				.thenReturn(mockResult);
		
		List<Transaction> result = service.searchByFilters(
				Optional.of(1), Optional.of(2),
				Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.of(1));
		
		assertEquals(2, result.size());
	}
	
	@Test
	void returnWithFilters() {
		LocalDateTime from = LocalDateTime.of(2023, 1, 1, 0, 0);
		LocalDateTime to = LocalDateTime.of(2023, 12, 31, 23, 59);
		when(repository.findByFilters(anyInt(), anyInt(), eq(from), eq(to), any(), eq(1)))
				.thenReturn(new ArrayList<>());
		
		List<Transaction> result = service.searchByFilters(
				Optional.of(1), Optional.of(10),
				Optional.of(from), Optional.of(to),
				Optional.empty(), Optional.of(1));
		
		assertNotNull(result);
	}
	
	@Test
	void returnWithWrongParams() {
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			service.searchByFilters(
					Optional.of(10), Optional.of(5),
					Optional.empty(), Optional.empty(),
					Optional.empty(), Optional.of(1));
		});
		
		assertTrue(exception.getMessage().contains("`from` должен быть ≤ `to`"));
	}
	
	@Test
	void returnWithNullCustomerId() {
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			service.searchByFilters(
					Optional.of(1), Optional.of(2),
					Optional.empty(), Optional.empty(),
					Optional.empty(), Optional.empty());
		});
		assertNotNull(exception);
	}
	
	private Transaction createTransaction(Long id) {
		Transaction tx = new Transaction();
		tx.setTransactionId(id);
		tx.setCustomerId(1);
		tx.setAmount(BigDecimal.TEN);
		tx.setDateTime(LocalDateTime.now());
		return tx;
	}
}
