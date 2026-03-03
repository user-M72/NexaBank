package BankApp.SpringBank.dto.res.role;

import java.util.UUID;

public record RoleResponseDto(

        UUID id,
        String name,
        String description

) {}
