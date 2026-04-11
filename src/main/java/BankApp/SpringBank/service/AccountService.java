package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.AccountCreateDto;
import BankApp.SpringBank.dto.res.account.AccountResponseDto;
import BankApp.SpringBank.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountResponseDto create(AccountCreateDto dto);

    AccountResponseDto block(UUID id);

    AccountResponseDto getAccount(UUID id);

    List<AccountResponseDto> getMyAccount();

    Account findById(UUID id);
}
