package BankApp.SpringBank.dto.req.user;

public record ProfileUpdatePasswordDto(

        String currentPassword,
        String newPassword,
        String confirmPassword
) {
}
