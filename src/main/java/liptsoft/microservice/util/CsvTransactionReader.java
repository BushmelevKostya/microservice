package liptsoft.microservice.util;

import jakarta.annotation.PostConstruct;
import liptsoft.microservice.builder.TransactionBuilder;
import liptsoft.microservice.model.Transaction;
import liptsoft.microservice.model.Type;
import liptsoft.microservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CsvTransactionReader {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private final String incomePath = "src/main/resources/data/incomes.csv";
    private final String outcomePath = "src/main/resources/data/outcomes.csv";
    private final TransactionRepository transactionRepository;

    @PostConstruct
    @Transactional
    public void initData() {
        if (transactionRepository.count() == 0) {
            List<Transaction> incomes = readTransactions(incomePath, Type.INCOME);
            List<Transaction> outcomes = readTransactions(outcomePath, Type.OUTCOME);
            
            transactionRepository.saveAll(incomes);
            transactionRepository.saveAll(outcomes);
        }
    }

    public List<Transaction> readTransactions(String path, Type type) {
        try (Scanner scanner = new Scanner(new FileReader(path))) {
            if (scanner.hasNextLine()) scanner.nextLine();

            return scanner.tokens()
                    .map(line -> parseLine(line, type))
                    .filter(Objects::nonNull)
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException("Ошибка чтения файла: " + path, e);
        }
    }

    private Transaction parseLine(String line, Type type) {
        try {
            String[] tokens = line.split(",");
            return new TransactionBuilder()
                    .setTransactionId(Long.parseLong(tokens[0]))
                    .setCustomerId(Integer.parseInt(tokens[1]))
                    .setAccountId(Long.parseLong(tokens[2]))
                    .setAmount(new BigDecimal(tokens[3]))
                    .setDateTime(LocalDateTime.parse(tokens[4], FORMATTER))
                    .setType(type)
                    .build();

        } catch (Exception e) {
            System.err.println("Ошибка при парсинге строки: " + line);
            return null;
        }
    }
}
