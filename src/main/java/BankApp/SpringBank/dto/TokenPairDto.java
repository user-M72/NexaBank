package BankApp.SpringBank.dto;

public record TokenPairDto(

        String accessToken,
        String refreshToken

) {
}
