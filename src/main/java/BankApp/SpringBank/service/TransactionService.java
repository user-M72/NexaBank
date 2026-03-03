package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.req.transaction.TransactionRequestDto;
import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    List<TransactionResponseDto> get();

    TransactionResponseDto getById(UUID id);

    TransactionResponseDto created(TransactionRequestDto dto);

    TransactionRequestDto updated(UUID id, TransactionRequestDto dto);

    void deleted(UUID id);
}
