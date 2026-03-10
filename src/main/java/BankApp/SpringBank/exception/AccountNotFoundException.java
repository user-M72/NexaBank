package BankApp.SpringBank.exception;

import BankApp.SpringBank.exception.handler.BankAppException;
import BankApp.SpringBank.exception.handler.ErrorCode;

import java.util.UUID;

public class AccountNotFoundException extends BankAppException {
    public AccountNotFoundException(UUID id) {
        super("Account not found by ID: " + id, ErrorCode.ACCOUNT_NOT_FOUND, 404);
    }
}
