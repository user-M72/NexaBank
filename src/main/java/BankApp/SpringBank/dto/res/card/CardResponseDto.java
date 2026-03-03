package BankApp.SpringBank.dto.res.card;

import BankApp.SpringBank.model.Enum.CardType;

import java.math.BigDecimal;
import java.util.UUID;

public record CardResponseDto(

        UUID id,
        String cardNumber,
        String cardHolderName,
        String expiryDate,
        CardType cardType,
        BigDecimal dailyLimit,
        boolean isActive

) {}
