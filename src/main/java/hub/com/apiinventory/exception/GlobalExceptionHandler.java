package hub.com.apiinventory.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Jakarta Validation
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleWebExchangeBindNotValid(WebExchangeBindException webe, ServerWebExchange exchange) {

        //filter msg
        String errorMsg = webe.getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMsg,
                exchange.getRequest().getPath().value(),
                "Method Argument Not Valid"
        );
        log.warn("BussinessException {} : validation ", exchange.getRequest().getPath());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    // Resource not found 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleResourceNotFound(ResourceNotFoundException rne, ServerWebExchange exchange) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                rne.getMessage(),
                exchange.getRequest().getPath().value(),
                "Resource Not Found"
        );
        log.warn("ResourceNotFound {} : value not found", exchange.getRequest().getPath());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));
    }
}
