package BankApp.SpringBank.dto.res.user;

import BankApp.SpringBank.dto.res.role.RoleResponseDto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record ProfileResponseDto(

        UUID id,
        String firstName,
        String lastName,
        String username,
        String email,
        Set<RoleResponseDto> roles,
        Instant createDate,
        Instant updateDate
) {
}
