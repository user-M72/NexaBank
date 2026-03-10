package BankApp.SpringBank.exception;

import BankApp.SpringBank.exception.handler.BankAppException;
import BankApp.SpringBank.exception.handler.ErrorCode;

import java.util.UUID;

public class UserNotFoundException extends BankAppException {
    public UserNotFoundException(UUID id) {
        super("User not found by ID: " + id, ErrorCode.USER_NOT_FOUND,  404);
    }
}
