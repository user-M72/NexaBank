package BankApp.SpringBank.exception;

public class RoleNotFoundByNameException extends RuntimeException {
    public RoleNotFoundByNameException(String roleName) {
        super("Role not found: " + roleName);
    }
}
