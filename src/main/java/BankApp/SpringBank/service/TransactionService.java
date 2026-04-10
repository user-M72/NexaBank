package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.DepositRequestDto;
import BankApp.SpringBank.dto.req.transaction.TransactionRequestDto;
import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionResponseDto transfer(TransactionRequestDto dto);

    TransactionResponseDto deposit(DepositRequestDto dto);

    List<TransactionResponseDto> getMyTransactions();
}
