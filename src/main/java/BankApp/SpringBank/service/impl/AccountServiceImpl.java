package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.req.account.AccountRequestDto;
import BankApp.SpringBank.dto.res.account.AccountResponseDto;
import BankApp.SpringBank.exception.AccountNotFoundException;
import BankApp.SpringBank.mapper.AccountMapper;
import BankApp.SpringBank.repository.AccountRepository;
import BankApp.SpringBank.service.AccountService;
import BankApp.SpringBank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return null;
    }

    @Override
    public AccountResponseDto created(AccountRequestDto dto) {
        return null;
    }

    @Override
    public AccountResponseDto updated(UUID id, AccountRequestDto dto) {
        return null;
    }

    @Override
    public void deleted(UUID id) {
        if (!accountRepository.existsById(id))
            throw new AccountNotFoundException(id);
        accountRepository.deleteById(id);
    }
}
