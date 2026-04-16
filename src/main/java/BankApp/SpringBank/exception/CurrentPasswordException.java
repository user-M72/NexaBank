    package BankApp.SpringBank.exception;

    public class CurrentPasswordException extends RuntimeException {
        public CurrentPasswordException() {
            super("Current password is incorrect");
        }
    }
