package liptsoft.microservice.util;

import liptsoft.microservice.builder.TransactionBuilder;
import liptsoft.microservice.model.Transaction;
import liptsoft.microservice.model.Type;

import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CsvTransactionReader {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

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
