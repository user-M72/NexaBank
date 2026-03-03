package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.req.transaction.TransactionRequestDto;
import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;
import BankApp.SpringBank.exception.TransactionNotFoundException;
import BankApp.SpringBank.mapper.TransactionMapper;
import BankApp.SpringBank.model.Transaction;
import BankApp.SpringBank.repository.TransactionRepository;
import BankApp.SpringBank.service.AccountService;
import BankApp.SpringBank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionMapper mapper;

    @Override
    public List<TransactionResponseDto> get() {
        return transactionRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public TransactionResponseDto getById(UUID id) {
        return null;
    }

    @Override
    public TransactionResponseDto created(TransactionRequestDto dto) {
        return null;
    }

    @Override
    public TransactionRequestDto updated(UUID id, TransactionRequestDto dto) {
        return null;
    }

    @Override
    public void deleted(UUID id) {
        if (!transactionRepository.existsById(id))
            throw new TransactionNotFoundException(id);
        transactionRepository.deleteById(id);
    }
}
