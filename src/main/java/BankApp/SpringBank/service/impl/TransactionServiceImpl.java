package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.mapper.TransactionMapper;
import BankApp.SpringBank.repository.TransactionRepository;
import BankApp.SpringBank.service.AccountService;
import BankApp.SpringBank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionMapper mapper;

}
