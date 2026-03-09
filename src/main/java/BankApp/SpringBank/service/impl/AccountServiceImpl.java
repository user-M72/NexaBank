package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.req.account.AccountRequestDto;
import BankApp.SpringBank.dto.res.account.AccountResponseDto;
import BankApp.SpringBank.exception.*;
import BankApp.SpringBank.mapper.AccountMapper;
import BankApp.SpringBank.model.Account;
import BankApp.SpringBank.model.User;
import BankApp.SpringBank.repository.AccountRepository;
import BankApp.SpringBank.service.AccountService;
import BankApp.SpringBank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final AccountMapper mapper;

    @Override
    public List<AccountResponseDto> get() {
        return accountRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public AccountResponseDto getById(UUID id) {
        Account account = findById(id);
        return mapper.toDto(account);
    }

    @Override
    @Transactional
    public AccountResponseDto created(AccountRequestDto dto) {
        User user = userService.findById(dto.userId());

        String accountNumber = "ACC-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();

        Account account = mapper.toEntity(dto, user, accountNumber);

        Account save = accountRepository.save(account);
        return mapper.toDto(save);

    }

    @Override
    public AccountResponseDto updated(UUID id, AccountRequestDto dto) {
        Account account = findById(id);
        mapper.updateFromDto(dto, account);
        Account saved = accountRepository.save(account);
        return mapper.toDto(saved);

    }

    @Override
    public void deleted(UUID id) {
        if (!accountRepository.existsById(id))
            throw new AccountNotFoundException(id);
        accountRepository.deleteById(id);
    }

    @Override
    @Transactional
    public AccountResponseDto deposit(UUID id, BigDecimal amount) {
        Account account = findById(id);
        checkAccountBlock(account);
        switch (account.getType()){
            case CHECKING -> {
                //No limits - any amount
            }

            case SAVINGS -> {
                if (amount.compareTo(BigDecimal.valueOf(100)) < 0){
                    throw new InvalidAmountException("Minimum deposit amount for savings account: 100");
                }
            }

            case CREDIT -> {
                if (account.getBalance().add(amount).compareTo(BigDecimal.ZERO) > 0){
                    throw new InvalidAmountException("You can't put more into a credit account than the debt!");
                }
            }
        }
        account.setBalance(account.getBalance().add(amount));
        Account saved = accountRepository.save(account);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public AccountResponseDto withdraw(UUID id, BigDecimal amount) {
        Account account = findById(id);
        checkAccountBlock(account);

        switch (account.getType()){
            case CHECKING -> {
                if (account.getBalance().compareTo(amount) < 0){
                    throw new InsufficientFundsException(account.getBalance(), amount);
                }
            }

            case SAVINGS -> {
                BigDecimal halfBalance = account.getBalance()
                        .divide(BigDecimal.valueOf(2));

                if (amount.compareTo(halfBalance) > 0){
                    throw new InsufficientFundsException(account.getBalance(), amount);
                }
            }

            case CREDIT -> {
                BigDecimal creditLimit = BigDecimal.valueOf(-10000);
                if (account.getBalance().subtract(amount).compareTo(creditLimit) < 0){
                    throw new InsufficientFundsException(account.getBalance(), amount);
                }
            }
        }

        account.setBalance(account.getBalance().subtract(amount));
        Account saved = accountRepository.save(account);
        return mapper.toDto(saved);
    }

    @Override
    public void block(UUID id) {
        Account account = findById(id);
        checkAccountBlock(account);
        account.setBlocked(true);
        accountRepository.save(account);
    }

    @Override
    public void unblock(UUID id) {
        Account account = findById(id);

        if (!account.isBlocked()){
            throw new AccountNotBlockedException(id);
        }

        account.setBlocked(false);
        accountRepository.save(account);
    }

    @Override
    public Account findById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(()->new AccountNotFoundException(id));
    }

    private void checkAccountBlock(Account account){
        if (account.isBlocked()){
            throw new AccountBlockedException(account.getId());
        }
    }
}
