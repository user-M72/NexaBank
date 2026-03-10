package BankApp.SpringBank.exception.handler;

import lombok.Getter;

@Getter
public class BankAppException extends RuntimeException {
    private final ErrorCode errorCode;
    private final int httpStatus;

    public BankAppException(String message, ErrorCode errorCode, int httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
