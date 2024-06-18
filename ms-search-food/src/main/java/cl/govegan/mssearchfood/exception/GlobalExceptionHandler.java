package cl.govegan.mssearchfood.exception;

import cl.govegan.mssearchfood.web.response.ApiEntityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFoodDataException.class)
    public ResponseEntity<ApiEntityResponse<String>> handleInvalidFoodDataException (InvalidFoodDataException ex) {
        return ResponseEntity.badRequest().body(ApiEntityResponse.<String>builder().message(ex.getMessage()).data(null).build());
    }
}
