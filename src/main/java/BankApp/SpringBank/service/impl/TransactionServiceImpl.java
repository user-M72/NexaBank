package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.DepositRequestDto;
import BankApp.SpringBank.dto.req.transaction.TransactionRequestDto;
import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;
import BankApp.SpringBank.mapper.TransactionMapper;
import BankApp.SpringBank.repository.TransactionRepository;
import BankApp.SpringBank.service.AccountService;
import BankApp.SpringBank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Override
    public TransactionResponseDto transfer(TransactionRequestDto dto) {
        return null;
    }

    @Override
    public TransactionResponseDto deposit(DepositRequestDto dto) {
        return null;
    }

    @Override
    public List<TransactionResponseDto> getMyTransactions() {
        return List.of();
    }
}
