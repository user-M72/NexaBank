package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.req.transaction.TransactionRequestDto;
import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;
import BankApp.SpringBank.exception.*;
import BankApp.SpringBank.mapper.TransactionMapper;
import BankApp.SpringBank.model.Account;
import BankApp.SpringBank.model.Enum.TransactionStatus;
import BankApp.SpringBank.model.Enum.TransactionType;
import BankApp.SpringBank.model.Transaction;
import BankApp.SpringBank.repository.TransactionRepository;
import BankApp.SpringBank.service.AccountService;
import BankApp.SpringBank.service.TransactionService;
import BankApp.SpringBank.service.SaveTransactionService;
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
    private final SaveTransactionService saveTransactionService;

    @Override
    public List<TransactionResponseDto> get() {
        return transactionRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public TransactionResponseDto getById(UUID id) {
        Transaction transaction = findTransactionById(id);
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

        if (fromAccount.getCurrency() != toAccount.getCurrency()){
            throw new CurrencyMismatchException(fromAccount.getCurrency(), toAccount.getCurrency());
        }

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .type(TransactionType.TRANSFER)
                .status(TransactionStatus.PENDING)
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .description("Transfer from " + fromId + " to " + toId)
                .referenceNumber(UUID.randomUUID().toString())
                .build();
        try {
            checkAccountNotBlocked(fromAccount);
            checkSufficientFunds(fromAccount, amount);

            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));

            transaction.setStatus(TransactionStatus.SUCCESS);
        } catch (Exception e) {
            saveTransactionService.saveTransaction(transaction);
            throw e;
        }
        Transaction saved = transactionRepository.save(transaction);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public TransactionResponseDto deposit(UUID accountId, BigDecimal amount) {
        Account account = accountService.findById(accountId);

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .type(TransactionType.DEPOSIT)
                .status(TransactionStatus.PENDING)
                .toAccount(account)
                .description("Deposit to " + accountId)
                .referenceNumber(UUID.randomUUID().toString())
                .build();

        try {
            checkAccountNotBlocked(account);
            checkSufficientFunds(account, amount);
            account.setBalance(account.getBalance().add(amount));
            transaction.setStatus(TransactionStatus.SUCCESS);
        } catch (Exception e) {
            saveTransactionService.saveTransaction(transaction);
            throw e;
        }

        Transaction saved = transactionRepository.save(transaction);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public TransactionResponseDto withdraw(UUID accountId, BigDecimal amount) {
        Account account = accountService.findById(accountId);

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .type(TransactionType.WITHDRAWAL)
                .status(TransactionStatus.PENDING)
                .fromAccount(account)
                .description("Withdrawal from " + accountId)
                .referenceNumber(UUID.randomUUID().toString())
                .build();

        try {
            checkAccountNotBlocked(account);
            checkSufficientFunds(account, amount);
            account.setBalance(account.getBalance().subtract(amount));
            transaction.setStatus(TransactionStatus.SUCCESS);
        } catch (Exception e) {
            saveTransactionService.saveTransaction(transaction);
            throw e;
        }
        Transaction saved = transactionRepository.save(transaction);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public TransactionResponseDto payment(UUID fromAccountId, BigDecimal amount, String description) {
        Account account = accountService.findById(fromAccountId);

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .type(TransactionType.PAYMENT)
                .status(TransactionStatus.PENDING)
                .fromAccount(account)
                .description(description)
                .referenceNumber(UUID.randomUUID().toString())
                .build();

        try {
            checkAccountNotBlocked(account);
            checkSufficientFunds(account, amount);
            account.setBalance(account.getBalance().subtract(amount));
            transaction.setStatus(TransactionStatus.SUCCESS);
        } catch (Exception e) {
            saveTransactionService.saveTransaction(transaction);
            throw e;
        }

        Transaction saved = transactionRepository.save(transaction);
        return mapper.toDto(saved);
    }

    @Override
    public void canceled(UUID transactionId) {
        Transaction transaction = findTransactionById(transactionId);

        if (transaction.getStatus() != TransactionStatus.PENDING){
            throw new CannotCancelTransactionException(transactionId);
        }

        if (transaction.getFromAccount() != null){
            Account fromAccount = transaction.getFromAccount();;
            fromAccount.setBalance(fromAccount.getBalance().add(transaction.getAmount()));
        }

        transaction.setStatus(TransactionStatus.CANCELLED);
        transactionRepository.save(transaction);
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
