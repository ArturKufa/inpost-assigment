package pl.arturkufa.product_discount.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.arturkufa.product_discount.domain.exception.BusinessException;
import pl.arturkufa.product_discount.domain.exception.TechnicalException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> "Field=%s, error=%s".formatted(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        log.info("Validation error occurred: "+ errors.stream().collect(Collectors.joining(",")));
        return new ResponseEntity<>(getErrorsMap("validation-errors", errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessExceptions(BusinessException ex) {
        log.info("Business error occurred: "+ ex.getMessage());
        return new ResponseEntity<>("Business exception occurred: %s".formatted(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<String> handleTechnicalExceptions(TechnicalException ex) {
        log.warn("Technical error occurred: ", ex);
        return new ResponseEntity<>("Something unexpected happen. Please contact anyone who can help you. Call paramedics!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, List<String>> getErrorsMap(String errorGroupName, List<String> errors) {
        final Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put(errorGroupName, errors);
        return errorResponse;
    }

}
