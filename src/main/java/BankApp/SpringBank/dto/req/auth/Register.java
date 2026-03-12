package BankApp.SpringBank.dto.req.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record Register(

        @NotBlank(message = "First name must not be blank")
        String firstName,

        @NotBlank(message = "Last name must not be blank")
        String lastName,

        @NotBlank(message = "Username must not be blank")
        String username,

        @NotBlank(message = "Email must not be null")
        @Email
        String email,

        @NotBlank(message = "Password must not be blank")
        String password

) {
}
