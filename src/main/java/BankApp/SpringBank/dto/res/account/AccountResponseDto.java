package BankApp.SpringBank.dto.res.account;

import BankApp.SpringBank.model.Enum.AccountStatus;
import BankApp.SpringBank.model.Enum.AccountType;
import BankApp.SpringBank.model.Enum.Currency;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record AccountResponseDto(

        UUID id,
        String accountNumber,
        BigDecimal balance,
        AccountType type,
        Currency currency,
        AccountStatus status,
        Instant createdDate

) {}
