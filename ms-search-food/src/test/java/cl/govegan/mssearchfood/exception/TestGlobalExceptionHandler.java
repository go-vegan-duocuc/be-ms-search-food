package cl.govegan.mssearchfood.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import cl.govegan.mssearchfood.web.response.ApiEntityResponse;

class TestGlobalExceptionHandler {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleInvalidFoodDataException() {
        InvalidFoodDataException ex = new InvalidFoodDataException("Test message");
        ResponseEntity<ApiEntityResponse<String>> response = globalExceptionHandler.handleInvalidFoodDataException(ex);

        assert response != null;
        ApiEntityResponse<String> body = response.getBody();
        assert body != null;
        assertEquals("Test message", body.getMessage());
        // Add more assertions as needed
    }
}