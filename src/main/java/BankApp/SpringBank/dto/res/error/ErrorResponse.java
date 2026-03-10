package BankApp.SpringBank.dto.res.error;

import java.time.LocalDateTime;

public record ErrorResponse(

        int status,
        String errorCode,
        String message,
        LocalDateTime timestamp
) {
}
