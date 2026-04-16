package BankApp.SpringBank.exception;

import java.util.UUID;

public class CurrentNotUserCardException extends RuntimeException {
    public CurrentNotUserCardException(UUID id) {
        super("The card does not belong to the current user: " + id);
    }
}
