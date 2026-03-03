package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;
import BankApp.SpringBank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction/v1")
public class TransactionApi {

    private final TransactionService service;

    @GetMapping
    public List<TransactionResponseDto> get(){
        return service.get();
    }
}
