package BankApp.SpringBank.exception;

import BankApp.SpringBank.exception.handler.BankAppException;
import BankApp.SpringBank.exception.handler.ErrorCode;

import java.util.UUID;

public class RoleNotFoundException extends BankAppException {
    public RoleNotFoundException(UUID id) {
        super("Role not found by ID: " + id, ErrorCode.ROLE_NOT_FOUND, 404);
    }
}
