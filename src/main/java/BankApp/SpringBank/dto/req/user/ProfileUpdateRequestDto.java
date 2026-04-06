package BankApp.SpringBank.dto.req.user;

public record ProfileUpdateRequestDto(

        String firstName,
        String lastName,
        String username,
        String email

) {
}
