package BankApp.SpringBank.exception;

public class CardInsufficientFundsException extends RuntimeException {
    public CardInsufficientFundsException() {
        super("Insufficient funds on the card");
    }
}
