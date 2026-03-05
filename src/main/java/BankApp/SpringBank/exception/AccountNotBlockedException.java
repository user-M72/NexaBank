package BankApp.SpringBank.exception;

import java.util.UUID;

public class AccountNotBlockedException extends RuntimeException {
    public AccountNotBlockedException(UUID id) {
        super("Account not blocked by ID: " + id);
    }
}
