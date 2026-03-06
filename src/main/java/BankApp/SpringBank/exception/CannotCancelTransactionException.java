package BankApp.SpringBank.exception;

import java.util.UUID;

public class CannotCancelTransactionException extends RuntimeException {
    public CannotCancelTransactionException(UUID id) {
        super("You cannot cancel a transaction with a status of not PENDING: " + id);
    }
}
