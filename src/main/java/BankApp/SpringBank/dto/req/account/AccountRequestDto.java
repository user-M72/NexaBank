package BankApp.SpringBank.dto.req.account;

import BankApp.SpringBank.model.Enum.AccountType;
import BankApp.SpringBank.model.Enum.Currency;

import java.util.UUID;

public record AccountRequestDto (


        AccountType type,
        Currency currency,
        UUID userId

){}
