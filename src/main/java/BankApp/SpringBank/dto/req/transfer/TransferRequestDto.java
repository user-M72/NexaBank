package BankApp.SpringBank.dto.req.transfer;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequestDto(

        UUID fromCardId,
        UUID toCardId,
        BigDecimal amount,
        String description

) {
}
