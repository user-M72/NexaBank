package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.AccountCreateDto;
import BankApp.SpringBank.dto.res.account.AccountResponseDto;
import BankApp.SpringBank.mapper.AccountMapper;
import BankApp.SpringBank.model.Account;
import BankApp.SpringBank.model.Enum.AccountStatus;
import BankApp.SpringBank.model.User;
import BankApp.SpringBank.repository.AccountRepository;
import BankApp.SpringBank.service.AccountService;
import BankApp.SpringBank.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final AuthService authService;
    private final AccountMapper mapper;

    @Override
    public List<AccountResponseDto> getMyAccounts() {
        User user = authService.getCurrentUser();
        return repository.findAllByOwner(user)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountResponseDto getAccountById(UUID id) {
        Account account = findById(id);
        return mapper.toDto(account);
    }

    @Override
    public AccountResponseDto createAccount(AccountCreateDto dto) {
        User user = authService.getCurrentUser();

        Account account = Account.builder()
                .bankName(dto.bankName())
                .accountNumber(generateAccountNumber())
                .balance(BigDecimal.ZERO)
                .type(dto.type())
                .currency(dto.currency())
                .status(AccountStatus.ACTIVE)
                .owner(user)
                .build();

        Account save = repository.save(account);
        return mapper.toDto(save);
    }

    @Override
    public AccountResponseDto changeStatus(UUID id, AccountStatus status) {
        Account account = findById(id);
        account.setStatus(status);
        Account save = repository.save(account);
        return mapper.toDto(save);
    }

    @Override
    public Account findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException(" Account not found by Id: " + id));
    }


    private String generateAccountNumber() {
        return "ACC-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
    }
}
