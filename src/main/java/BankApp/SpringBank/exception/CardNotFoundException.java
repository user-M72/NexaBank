package BankApp.SpringBank.exception;

import java.util.UUID;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(UUID id) {
        super("Card not found by ID: " + id);
    }
}
