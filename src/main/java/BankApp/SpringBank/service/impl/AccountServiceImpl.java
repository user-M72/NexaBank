package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.req.account.AccountRequestDto;
import BankApp.SpringBank.dto.res.account.AccountResponseDto;
import BankApp.SpringBank.exception.AccountBlockedException;
import BankApp.SpringBank.exception.AccountNotBlockedException;
import BankApp.SpringBank.exception.AccountNotFoundException;
import BankApp.SpringBank.exception.InsufficientFundsException;
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

    private Account accountFindById(UUID id){
        return accountRepository.findById(id)
                .orElseThrow(()-> new AccountNotFoundException(id));
    }

    private void checkAccountBlock(Account account){
        if (account.isBlocked()){
            throw new AccountBlockedException(account.getId());
        }
    }

    @Override
    public List<AccountResponseDto> get() {
        return accountRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public AccountResponseDto getById(UUID id) {
        Account account = accountFindById(id);
        return mapper.toDto(account);
    }

    @Override
    public AccountResponseDto created(AccountRequestDto dto) {
        User user = userService.findById(dto.userId());

        String accountNumber = "ACC-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();

        Account account = mapper.toEntity(dto, user, accountNumber);

        Account save = accountRepository.save(account);
        return mapper.toDto(save);

    }

    @Override
    public AccountResponseDto updated(UUID id, AccountRequestDto dto) {
        Account account = accountFindById(id);
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
        Account account = accountFindById(id);
        checkAccountBlock(account);
        account.setBalance(account.getBalance().add(amount));
        Account saved = accountRepository.save(account);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public AccountResponseDto withdraw(UUID id, BigDecimal amount) {
        Account account = accountFindById(id);
        checkAccountBlock(account);

        if (account.getBalance().compareTo(amount) < 0){
            throw new InsufficientFundsException(account.getBalance(), amount);
        }

        account.setBalance(account.getBalance().subtract(amount));
        Account saved = accountRepository.save(account);
        return mapper.toDto(saved);
    }

    @Override
    public void block(UUID id) {
        Account account = accountFindById(id);
        checkAccountBlock(account);
        account.setBlocked(true);
        accountRepository.save(account);
    }

    @Override
    public void unblock(UUID id) {
        Account account = accountFindById(id);

        if (!account.isBlocked()){
            throw new AccountNotBlockedException(id);
        }

        account.setBlocked(false);
        accountRepository.save(account);
    }
}
