package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.AccountCreateDto;
import BankApp.SpringBank.dto.res.account.AccountResponseDto;
import BankApp.SpringBank.model.Enum.AccountStatus;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    List<AccountResponseDto> getMyAccounts();

    AccountResponseDto getAccountById(UUID id);

    AccountResponseDto createAccount(AccountCreateDto dto);

    AccountResponseDto changeStatus(UUID id, AccountStatus status);
}
