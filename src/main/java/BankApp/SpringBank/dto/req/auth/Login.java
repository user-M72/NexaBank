package BankApp.SpringBank.dto.req.auth;

import jakarta.validation.constraints.NotBlank;

public record Login(
        @NotBlank(message = "Username must not be blank")
        String username,
        @NotBlank(message = "Password must not be blank")
        String password
) {
}
