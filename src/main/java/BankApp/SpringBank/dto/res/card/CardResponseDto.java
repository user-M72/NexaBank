package BankApp.SpringBank.dto.res.card;

import BankApp.SpringBank.model.Enum.CardType;
import BankApp.SpringBank.model.Enum.Currency;

import java.math.BigDecimal;
import java.util.UUID;

public record CardResponseDto(

        UUID id,
        String cardNumber,
        String expirationDate,
        BigDecimal balance,
        Currency currency,
        CardType type,
        boolean blocked

) {}
