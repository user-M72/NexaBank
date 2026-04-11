package BankApp.SpringBank.dto.res.transaction;

import BankApp.SpringBank.model.Enum.TransactionStatus;
import BankApp.SpringBank.model.Enum.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionResponseDto(

        UUID id,
        BigDecimal amount,
        TransactionStatus status,
        TransactionType type,
        UUID fromCardId,
        UUID toCardId,
        String description,
        Instant createdDate

) {}
