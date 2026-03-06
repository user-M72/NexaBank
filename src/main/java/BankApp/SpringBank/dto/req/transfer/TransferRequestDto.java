package BankApp.SpringBank.dto.req.transfer;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequestDto(

        UUID fromAccount,
        UUID toAccount,
        BigDecimal amount,
        String description

) {
}
