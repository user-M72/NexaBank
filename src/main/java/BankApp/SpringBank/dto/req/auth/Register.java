package BankApp.SpringBank.dto.req.auth;

public record Register(

        String firstName,
        String lastName,
        String username,
        String email,
        String password

) {
}
