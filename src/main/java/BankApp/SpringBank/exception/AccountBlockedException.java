package BankApp.SpringBank.exception;

import java.util.UUID;

public class AccountBlockedException extends RuntimeException {
    public AccountBlockedException(UUID id) {
        super("Account blocked by ID: " + id);
    }
}
