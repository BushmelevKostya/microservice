package liptsoft.microservice.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("status", ex.getStatusCode().value());
		body.put("error", ex.getReason());
		return new ResponseEntity<>(body, ex.getStatusCode());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("status", 500);
		body.put("error", "Внутренняя ошибка сервера");
		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
