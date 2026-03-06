package BankApp.SpringBank.exception;

public class InvalidCardTypeException extends RuntimeException {
    public InvalidCardTypeException(String message) {
        super(message);
    }
}
