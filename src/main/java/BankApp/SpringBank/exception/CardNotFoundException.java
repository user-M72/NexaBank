package BankApp.SpringBank.exception;

import BankApp.SpringBank.exception.handler.BankAppException;
import BankApp.SpringBank.exception.handler.ErrorCode;

import java.util.UUID;

public class CardNotFoundException extends BankAppException {
    public CardNotFoundException(UUID id) {
        super("Card not found by ID: " + id, ErrorCode.CARD_NOT_FOUND, 404);
    }
}
