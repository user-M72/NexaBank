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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final AuthService authService;
    private final AccountMapper mapper;


    @Override
    @Transactional
    public AccountResponseDto create(AccountCreateDto dto) {
        User user = authService.getCurrentUser();

        Account account = Account.builder()
                .bankName(dto.bankName())
                .type(dto.type())
                .status(AccountStatus.ACTIVE)
                .blocked(false)
                .owner(user)
                .build();

        Account saved = repository.save(account);
        return mapper.toDto(saved);
    }

    @Override
    public AccountResponseDto block(UUID id) {
        Account account = findAndCheck(id);
        account.setBlocked(true);
        return mapper.toDto(account);
    }

    @Override
    public AccountResponseDto getAccount(UUID id) {

        Account account =  findAndCheck(id);
        return mapper.toDto(account);
    }

    @Override
    public List<AccountResponseDto> getMyAccount() {
        User user = authService.getCurrentUser();
        return repository.findAllByOwner(user)
                .stream().map(mapper::toDto)
                .toList();
    }

    @Override
    public Account findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException(" Account not found by Id: " + id));
    }

    private Account findAndCheck(UUID id){
        User user = authService.getCurrentUser();
        Account account = findById(id);

        if (!account.getOwner().getId().equals(user.getId())){
            throw new RuntimeException("No access to account: " + id);
        }

        return account;
    }

}
