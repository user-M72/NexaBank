package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.AccountCreateDto;
import BankApp.SpringBank.dto.res.account.AccountResponseDto;
import BankApp.SpringBank.model.Enum.AccountStatus;
import BankApp.SpringBank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Override
    public List<AccountResponseDto> getMyAccounts() {
        return List.of();
    }

    @Override
    public AccountResponseDto getAccountById(UUID id) {
        return null;
    }

    @Override
    public AccountResponseDto createAccount(AccountCreateDto dto) {
        return null;
    }

    @Override
    public AccountResponseDto changeStatus(UUID id, AccountStatus status) {
        return null;
    }
}
