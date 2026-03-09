package BankApp.SpringBank.dto.req.role;

import jakarta.validation.constraints.NotBlank;

public record RoleRequestDto(

        @NotBlank(message = "Role name must not be blank")
        String name,
        @NotBlank(message = "Role description must not be blank")
        String description

) {
}
