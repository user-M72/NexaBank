package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.res.account.AccountResponseDto;
import BankApp.SpringBank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/v1")
public class AccountApi {

    private final AccountService service;

    @GetMapping
    public List<AccountResponseDto> get(){
        return service.get();
    }
}
