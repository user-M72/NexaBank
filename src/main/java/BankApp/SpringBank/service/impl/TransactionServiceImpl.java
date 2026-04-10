package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.DepositRequestDto;
import BankApp.SpringBank.dto.req.transaction.TransactionRequestDto;
import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;
import BankApp.SpringBank.mapper.TransactionMapper;
import BankApp.SpringBank.model.Account;
import BankApp.SpringBank.model.Enum.AccountStatus;
import BankApp.SpringBank.model.Enum.TransactionStatus;
import BankApp.SpringBank.model.Enum.TransactionType;
import BankApp.SpringBank.model.Transaction;
import BankApp.SpringBank.repository.TransactionRepository;
import BankApp.SpringBank.service.AccountService;
import BankApp.SpringBank.service.AuthService;
import BankApp.SpringBank.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final AccountService accountService;
    private final TransactionMapper mapper;
    private final AuthService authService;

    @Override
    @Transactional
    public TransactionResponseDto transfer(TransactionRequestDto dto) {
        Account from = accountService.findById(dto.fromAccountId());
        Account to = accountService.findById(dto.toAccountId());

        if (from.getStatus() != AccountStatus.ACTIVE){
            throw new RuntimeException("Source account is not active");
        }

        if (to.getStatus() != AccountStatus.ACTIVE){
            throw new RuntimeException("Target account is not active");
        }

        if (from.getBalance().compareTo(dto.amount()) < 0){
            throw new RuntimeException("Insufficient funds");
        }

        if (from.getCurrency() != to.getCurrency()){
            throw new RuntimeException("Currency mismatch");
        }

        from.setBalance(from.getBalance().subtract(dto.amount()));
        to.setBalance(to.getBalance().add(dto.amount()));

        Transaction transaction = Transaction.builder()
                .amount(dto.amount())
                .type(TransactionType.TRANSFER)
                .status(TransactionStatus.SUCCESS)
                .description(dto.description())
                .referenceNumber(generateReference())
                .fromAccount(from)
                .toAccount(to)
                .build();

        Transaction save = repository.save(transaction);
        return mapper.toDto(save);
    }

    @Override
    @Transactional
    public TransactionResponseDto deposit(DepositRequestDto dto) {
        Account account = accountService.findById(dto.accountId());

        account.setBalance(account.getBalance().add(dto.amount()));

        Transaction transaction = Transaction.builder()
                .amount(dto.amount())
                .type(TransactionType.DEPOSIT)
                .status(TransactionStatus.SUCCESS)
                .description("DEPOSIT")
                .referenceNumber(generateReference())
                .toAccount(account)
                .build();

        Transaction save = repository.save(transaction);
        return mapper.toDto(save);
    }

    @Override
    @Transactional
    public List<TransactionResponseDto> getMyTransactions() {
        return repository.findAllByFromAccount_OwnerOrToAccount_Owner(
                    authService.getCurrentUser(),
                    authService.getCurrentUser()
                )
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    private String generateReference() {
        return "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
}
