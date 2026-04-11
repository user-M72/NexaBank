package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.AccountCreateDto;
import BankApp.SpringBank.dto.res.account.AccountResponseDto;
import BankApp.SpringBank.model.Enum.AccountStatus;
import BankApp.SpringBank.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/account/v1")
@RequiredArgsConstructor
public class AccountApi {

    private final AccountService service;

    @PostMapping("/create")
    public ResponseEntity<AccountResponseDto> createAccount(@RequestBody AccountCreateDto dto){
        AccountResponseDto account = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @GetMapping("/my")
    public ResponseEntity<List<AccountResponseDto>> getMyAccount(){
        List<AccountResponseDto> accounts = service.getMyAccount();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable("accountId") UUID accountId){
        AccountResponseDto account = service.getAccount(accountId);
        return ResponseEntity.ok(account);
    }

    @PatchMapping("/{accountId}/block")
    public ResponseEntity<AccountResponseDto> changeStatus(@PathVariable("accountId") UUID accountId){
        AccountResponseDto changeStatus = service.block(accountId);
        return ResponseEntity.ok(changeStatus);
    }
}
