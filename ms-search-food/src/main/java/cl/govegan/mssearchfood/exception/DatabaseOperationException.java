package cl.govegan.mssearchfood.exception;

public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException (String message) {
        super(message);
    }
}
