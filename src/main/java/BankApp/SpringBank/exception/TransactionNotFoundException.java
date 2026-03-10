package BankApp.SpringBank.exception;

import BankApp.SpringBank.exception.handler.BankAppException;
import BankApp.SpringBank.exception.handler.ErrorCode;

import java.util.UUID;

public class TransactionNotFoundException extends BankAppException {
    public TransactionNotFoundException(UUID id) {
        super("Transaction not found by ID: " + id, ErrorCode.TRANSACTION_NOT_FOUND, 404);
    }
}
