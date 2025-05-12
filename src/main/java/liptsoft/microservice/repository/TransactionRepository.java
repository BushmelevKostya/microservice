package liptsoft.microservice.repository;

import liptsoft.microservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	@Query(value = """
			    SELECT * FROM transaction t
			    WHERE t.customer_id = :customerId
			      AND (:accountId IS NULL OR t.account_id = :accountId)
			      AND t.date_time >= :startTime
			      AND t.date_time <= :endTime
			    ORDER BY t.date_time
			    OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY
			""", nativeQuery = true)
	List<Transaction> findByFilters(
			@Param("offset") int offset,
			@Param("limit") int limit,
			@Param("startTime") LocalDateTime startTime,
			@Param("endTime") LocalDateTime endTime,
			@Param("accountId") Long accountId,
			@Param("customerId") Integer customerId
	);
}

