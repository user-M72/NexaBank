package BankApp.SpringBank.dto.res;

import java.util.Set;
import java.util.UUID;

public record UserResponseDto(

        UUID id,
        String firstName,
        String lastName,
        String username,
        String email,
        String password,
        Set<RoleResponseDto> roles
) {}
