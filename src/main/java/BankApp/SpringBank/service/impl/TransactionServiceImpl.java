package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.req.transaction.TransactionRequestDto;
import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;
import BankApp.SpringBank.exception.AccountBlockedException;
import BankApp.SpringBank.exception.InsufficientFundsException;
import BankApp.SpringBank.exception.TransactionNotFoundException;
import BankApp.SpringBank.mapper.TransactionMapper;
import BankApp.SpringBank.model.Account;
import BankApp.SpringBank.model.Enum.TransactionStatus;
import BankApp.SpringBank.model.Enum.TransactionType;
import BankApp.SpringBank.model.Transaction;
import BankApp.SpringBank.repository.TransactionRepository;
import BankApp.SpringBank.service.AccountService;
import BankApp.SpringBank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(()-> new TransactionNotFoundException(id));
        return mapper.toDto(transaction);
    }

    @Override
    @Transactional
    public TransactionResponseDto created(TransactionRequestDto dto) {
        Account fromAccount = accountService.findById(dto.fromAccountId());
        Account toAccount = accountService.findById(dto.toAccountId());

        checkAccountNotBlocked(fromAccount);
        checkSufficientFunds(fromAccount, dto.amount());

        fromAccount.setBalance(fromAccount.getBalance().subtract(dto.amount()));
        toAccount.setBalance(toAccount.getBalance().add(dto.amount()));

        String referenceNumber = UUID.randomUUID().toString();

        Transaction transaction = mapper.toEntity(dto, fromAccount, toAccount, referenceNumber);
        transaction.setStatus(TransactionStatus.SUCCESS);

        Transaction saved = transactionRepository.save(transaction);
        return mapper.toDto(saved);
    }

    @Override
    public List<TransactionResponseDto> getByAccountId(UUID accountId) {
        Account account = accountService.findById(accountId);

        List<Transaction> transactions = transactionRepository.findByFromAccountOrToAccount(account, account);

        return transactions.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public TransactionResponseDto transfer(UUID fromId, UUID toId, BigDecimal amount) {
        Account fromAccount = accountService.findById(fromId);
        Account toAccount = accountService.findById(toId);

        checkAccountNotBlocked(fromAccount);
        checkSufficientFunds(fromAccount, amount);

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .type(TransactionType.TRANSFER)
                .status(TransactionStatus.SUCCESS)
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .description("Transfer from " + fromId + "to" + toId)
                .referenceNumber(UUID.randomUUID().toString())
                .build();

        Transaction saved = transactionRepository.save(transaction);
        return mapper.toDto(saved);
    }

    private Transaction findTransactionById(UUID id){
        return transactionRepository.findById(id)
                .orElseThrow(()-> new TransactionNotFoundException(id));
    }

    private void checkAccountNotBlocked(Account account){
        if (account.isBlocked()){
            throw new AccountBlockedException(account.getId());
        }
    }

    private void checkSufficientFunds(Account account, BigDecimal amount){
        if (account.getBalance().compareTo(amount) < 0){
            throw new InsufficientFundsException(account.getBalance(), amount);
        }
    }

}
