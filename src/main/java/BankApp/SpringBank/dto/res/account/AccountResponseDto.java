package BankApp.SpringBank.dto.res.account;

import BankApp.SpringBank.dto.res.card.CardResponseDto;
import BankApp.SpringBank.model.Enum.AccountStatus;
import BankApp.SpringBank.model.Enum.AccountType;
import BankApp.SpringBank.model.Enum.Currency;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record AccountResponseDto(

        UUID id,
        String bankName,
        AccountType type,
        AccountStatus status,
        boolean blocked,
        List<CardResponseDto> cards,
        Instant createdDate

) {}
