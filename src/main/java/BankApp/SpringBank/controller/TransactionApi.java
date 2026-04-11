package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.DepositRequestDto;
import BankApp.SpringBank.dto.req.transfer.TransferRequestDto;
import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;
import BankApp.SpringBank.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction/v1")
@AllArgsConstructor
public class TransactionApi {

    private final TransactionService service;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDto> transfer(@RequestBody TransferRequestDto dto){
        TransactionResponseDto transfer = service.transfer(dto);
        return ResponseEntity.ok(transfer);
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDto> deposit(@RequestBody DepositRequestDto dto){
        TransactionResponseDto deposit = service.deposit(dto);
        return ResponseEntity.ok(deposit);
    }


}
