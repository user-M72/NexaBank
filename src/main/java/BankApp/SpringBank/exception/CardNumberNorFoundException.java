package BankApp.SpringBank.exception;

public class CardNumberNorFoundException extends RuntimeException {
    public CardNumberNorFoundException(String cardNumber) {
        super("Card not found by card number: " + cardNumber);
    }
}
