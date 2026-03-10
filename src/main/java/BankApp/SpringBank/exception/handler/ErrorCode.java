package BankApp.SpringBank.exception.handler;

public enum ErrorCode {

    // 404 — Not Found
    USER_NOT_FOUND,
    ACCOUNT_NOT_FOUND,
    ROLE_NOT_FOUND,
    CARD_NOT_FOUND,
    TRANSACTION_NOT_FOUND,

    // 403 — Forbidden
    ACCOUNT_BLOCKED,
    CARD_BLOCKED,

    // 400 — Bad Request
    ACCOUNT_NOT_BLOCKED,
    CARD_NOT_BLOCKED,
    ACCOUNT_ALREADY_EXISTS,
    INSUFFICIENT_FUNDS,
    CURRENCY_MISMATCH,
    INVALID_AMOUNT,
    INVALID_CARD_TYPE,
    CANNOT_CANCEL_TRANSACTION,

    // 500 — Internal Server Error
    INTERNAL_ERROR
}
