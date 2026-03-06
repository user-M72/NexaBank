package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.req.transaction.TransactionRequestDto;
import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;
import BankApp.SpringBank.mapper.TransactionMapper;
import BankApp.SpringBank.repository.TransactionRepository;
import BankApp.SpringBank.service.AccountService;
import BankApp.SpringBank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public List<TransactionResponseDto> getByAccountId(UUID accountId) {
        return List.of();
    }

    @Override
    public TransactionResponseDto transfer(UUID fromId, UUID toId, BigDecimal amount) {
        return null;
    }

}
