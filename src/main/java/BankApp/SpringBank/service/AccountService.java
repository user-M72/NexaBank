package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.req.account.AccountRequestDto;
import BankApp.SpringBank.dto.res.account.AccountResponseDto;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    List<AccountResponseDto> get();

    AccountResponseDto getById(UUID id);

    AccountResponseDto created(AccountRequestDto dto);

    AccountResponseDto updated(UUID id, AccountRequestDto dto);

    void deleted(UUID id);
}
