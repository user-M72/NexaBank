package BankApp.SpringBank.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record DepositRequestDto(

        UUID cardId,
        BigDecimal amount
) {
}
