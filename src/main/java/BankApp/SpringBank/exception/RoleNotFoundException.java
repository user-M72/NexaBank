package BankApp.SpringBank.exception;

import java.util.UUID;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(UUID id) {
        super("Role not found by ID: " + id);
    }
}
