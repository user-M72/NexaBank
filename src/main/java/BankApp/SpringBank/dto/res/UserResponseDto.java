package BankApp.SpringBank.dto.res;

import BankApp.SpringBank.model.Enum.RoleEnum;

import java.util.Set;
import java.util.UUID;

public record UserResponseDto(

        UUID id,
        String FirstName,
        String LastName,
        String username,
        String email,
        String password,
        Set<RoleEnum> roles
) {}
