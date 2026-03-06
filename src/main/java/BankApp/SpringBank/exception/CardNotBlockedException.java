package BankApp.SpringBank.exception;

import java.util.UUID;

public class CardNotBlockedException extends RuntimeException {
    public CardNotBlockedException(UUID id) {
        super("Card not blocked by ID: " + id);
    }
}
