package BankApp.SpringBank.dto;

import BankApp.SpringBank.model.Enum.CardType;

import java.math.BigDecimal;
import java.util.UUID;

public record CardCreateDto(

        UUID accountId,
        String cardHolderName,
        CardType cardType,
        BigDecimal dailyLimit
) {
}
