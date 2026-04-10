package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.DepositRequestDto;
import BankApp.SpringBank.dto.req.transaction.TransactionRequestDto;
import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;
import BankApp.SpringBank.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction/v1")
@AllArgsConstructor
public class TransactionApi {

    private final TransactionService service;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDto> transfer(@RequestBody TransactionRequestDto dto){
        TransactionResponseDto transfer = service.transfer(dto);
        return ResponseEntity.ok(transfer);
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDto> deposit(@RequestBody DepositRequestDto dto){
        TransactionResponseDto deposit = service.deposit(dto);
        return ResponseEntity.ok(deposit);
    }

    @GetMapping("my")
    public ResponseEntity<List<TransactionResponseDto>> getMyTransaction(){
        List<TransactionResponseDto> myTransactions = service.getMyTransactions();
        return ResponseEntity.ok(myTransactions);
    }
}
