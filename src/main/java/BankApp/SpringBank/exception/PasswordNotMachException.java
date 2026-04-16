package BankApp.SpringBank.exception;

public class PasswordNotMachException extends RuntimeException {
    public PasswordNotMachException(String message) {
        super("Passwords do not match: " + message);
    }
}
