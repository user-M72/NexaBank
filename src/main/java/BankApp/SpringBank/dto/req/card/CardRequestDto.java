package BankApp.SpringBank.dto.req.card;

import BankApp.SpringBank.model.Enum.CardType;

import java.math.BigDecimal;
import java.util.UUID;

public record CardRequestDto(

        String cardHolderName,
        String expiryDate,
        CardType cardType,
        BigDecimal dailyLimit,
        UUID accountId
) {
}
