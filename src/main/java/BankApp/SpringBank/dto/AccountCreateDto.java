package BankApp.SpringBank.dto;

import BankApp.SpringBank.model.Enum.AccountType;
import BankApp.SpringBank.model.Enum.Currency;

public record AccountCreateDto(

        String bankName,
        AccountType type,
        Currency currency
) {
}
