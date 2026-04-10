package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.res.account.AccountResponseDto;
import BankApp.SpringBank.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account/v1")
@AllArgsConstructor
public class AccountApi {

    private final AccountService service;

    @PostMapping("/create")
    public ResponseEntity<AccountResponseDto> createAccount(){
        return null;
    }

    @GetMapping("/my")
    public ResponseEntity<AccountResponseDto> getMyAccount(){
        return null;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDto> getAccountById(){
        return null;
    }

    @PatchMapping("/{accountId}/status")
    public ResponseEntity<AccountResponseDto> changeStatus(){
        return null;
    }
}
