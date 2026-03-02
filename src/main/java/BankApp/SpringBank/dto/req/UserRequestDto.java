package BankApp.SpringBank.dto.req;

import BankApp.SpringBank.model.Enum.RoleEnum;

import java.util.Set;

public record UserRequestDto(

        String FirstName,
        String LastName,
        String username,
        String email,
        String password,
        Set<RoleEnum> roles
) {}
