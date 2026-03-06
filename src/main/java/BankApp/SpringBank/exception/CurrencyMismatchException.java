package BankApp.SpringBank.exception;

import BankApp.SpringBank.model.Account;
import BankApp.SpringBank.model.Enum.Currency;

public class CurrencyMismatchException extends RuntimeException {
    public CurrencyMismatchException(Currency fromAccount, Currency toAccount) {
        super("Currency mismatch: " + fromAccount + " or " + toAccount);
    }
}
