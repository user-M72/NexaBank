package BankApp.SpringBank.dto.req.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record UserRequestDto(

        @NotBlank(message = "First name must not be blank")
        @Size(max=30,min = 5, message = "First name must not exceed 30 characters")
        String firstName,

        @NotBlank(message = "Last name must not be blank")
        @Size(max=30,min = 5, message = "Last name must not exceed 30 characters")
        String lastName,

        @NotBlank(message = "Username must not be blank")
        @Size(max=30,min = 5, message = "Username must not exceed 30 characters")
        String username,

        @Email(message = "Email should be valid")
        @Size(max=60, message = "Email must not exceed 60 characters")
        String email,

        @NotBlank(message = "Password must not be blank")
        @Size(message = "Password must be at least 8 characters long", min = 8, max = 40)
        String password,

        @NotNull(message = "Role IDs must not be null")
        List<UUID> roleId
) {}
