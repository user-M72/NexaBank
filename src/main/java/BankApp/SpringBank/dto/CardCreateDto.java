package BankApp.SpringBank.dto;

import BankApp.SpringBank.model.Enum.CardType;
import BankApp.SpringBank.model.Enum.Currency;

import java.math.BigDecimal;
import java.util.UUID;

public record CardCreateDto(

        UUID accountId,
        CardType type,
        Currency currency
) {
}
