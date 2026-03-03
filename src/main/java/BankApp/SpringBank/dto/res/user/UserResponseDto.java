package BankApp.SpringBank.dto.res.user;

import BankApp.SpringBank.dto.res.role.RoleResponseDto;

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
