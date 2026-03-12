package BankApp.SpringBank.exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String message) {
        super("Username not found: " + message);
    }
}
