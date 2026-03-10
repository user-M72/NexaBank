package BankApp.SpringBank.exception.handler;

import BankApp.SpringBank.dto.res.error.ErrorResponse;
import BankApp.SpringBank.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFunds(InsufficientFundsException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        400,
                        "TRANSACTION_FAILED",
                        ex.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            AccountNotFoundException.class,
            CardNotFoundException.class,
            TransactionNotFoundException.class,
            RoleNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFound(BankAppException ex){
        log.error("Status: {}, Message: {}",
                ex.getHttpStatus(),
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        404,
                        ex.getErrorCode().name(),
                        ex.getMessage(),
                        LocalDateTime.now()

                ));
    }
}
