package BankApp.SpringBank.dto.res.auth;

public record AuthResponseDto(

        String accessToken,
        String refreshToken

) {
}
