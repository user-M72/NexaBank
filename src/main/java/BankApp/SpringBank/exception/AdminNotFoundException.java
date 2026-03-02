package BankApp.SpringBank.exception;

public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(String admin) {
        super("Admin not found : " + admin);
    }
}
