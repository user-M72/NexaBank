package BankApp.SpringBank.dto.req;

import java.util.List;
import java.util.UUID;

public record UserRequestDto(

        String firstName,
        String lastName,
        String username,
        String email,
        String password,
        List<UUID> roleId
) {}
