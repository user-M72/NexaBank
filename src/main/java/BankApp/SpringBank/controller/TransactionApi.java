package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;
import BankApp.SpringBank.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction/v1")
@AllArgsConstructor
public class TransactionApi {

    private final TransactionService service;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDto> transfer(){
        return null;
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDto> deposit(){
        return null;
    }

    @GetMapping("my")
    public ResponseEntity<TransactionResponseDto> getMyTransaction(){
        return null;
    }
}
