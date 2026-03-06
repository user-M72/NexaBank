package BankApp.SpringBank.exception;

import java.util.UUID;

public class CardBlockedException extends RuntimeException {
    public CardBlockedException(UUID id) {
        super("Card is blocked by ID: " + id);
    }
}
