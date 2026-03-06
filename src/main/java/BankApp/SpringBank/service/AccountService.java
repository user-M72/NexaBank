package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.req.account.AccountRequestDto;
import BankApp.SpringBank.dto.res.account.AccountResponseDto;
import BankApp.SpringBank.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    List<AccountResponseDto> get();

    AccountResponseDto getById(UUID id);

    AccountResponseDto created(AccountRequestDto dto);

    AccountResponseDto updated(UUID id, AccountRequestDto dto);

    void deleted(UUID id);

    AccountResponseDto deposit(UUID id, BigDecimal amount);

    AccountResponseDto withdraw(UUID id, BigDecimal amount);

    void block(UUID id);

    void unblock(UUID id);

    Account findById(UUID id);
}
