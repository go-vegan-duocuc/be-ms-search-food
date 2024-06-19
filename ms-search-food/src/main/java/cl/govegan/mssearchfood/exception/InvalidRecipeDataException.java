package cl.govegan.mssearchfood.exception;

public class InvalidRecipeDataException extends RuntimeException {
    public InvalidRecipeDataException (String message) {
        super(message);
    }
}