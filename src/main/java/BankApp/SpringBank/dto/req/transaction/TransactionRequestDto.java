package BankApp.SpringBank.dto.req.transaction;

import BankApp.SpringBank.model.Enum.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequestDto(

        BigDecimal amount,
        TransactionType type,
        String description,
        UUID fromAccountId,
        UUID toAccountId

) {}
