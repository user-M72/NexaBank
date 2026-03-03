package BankApp.SpringBank.dto.req.user;

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
