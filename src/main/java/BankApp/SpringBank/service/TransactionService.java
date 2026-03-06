package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.req.transaction.TransactionRequestDto;
import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    List<TransactionResponseDto> get();

    TransactionResponseDto getById(UUID id);

    TransactionResponseDto created(TransactionRequestDto dto);

    List<TransactionResponseDto> getByAccountId(UUID accountId);

    TransactionResponseDto transfer(UUID fromId, UUID toId, BigDecimal amount);

    TransactionResponseDto  deposit(UUID accountId, BigDecimal amount);

    TransactionResponseDto  withdraw(UUID accountId, BigDecimal amount);

    TransactionResponseDto payment(UUID fromAccountId, BigDecimal amount, String description);

    void canceled(UUID transactionId);
}
