package liptsoft.microservice;

import liptsoft.microservice.model.Transaction;
import liptsoft.microservice.model.Type;
import liptsoft.microservice.util.CsvTransactionReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MicroserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(MicroserviceApplication.class, args);
	}
}
