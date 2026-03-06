package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.req.transaction.TransactionRequestDto;
import BankApp.SpringBank.dto.req.transfer.TransferRequestDto;
import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;
import BankApp.SpringBank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction/v1")
public class TransactionApi {

    private final TransactionService service;

    @GetMapping
    public List<TransactionResponseDto> get(){
        return service.get();
    }

    @GetMapping("/{transactionId}")
    public TransactionResponseDto getById(@PathVariable("transactionId") UUID id){
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDto> created(@RequestBody TransactionRequestDto dto){
        TransactionResponseDto created = service.created(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponseDto>> getByAccountId(@PathVariable("accountId") UUID id){
        List<TransactionResponseDto> accountId = service.getByAccountId(id);
        return ResponseEntity.ok(accountId);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDto> transfer(@RequestBody TransferRequestDto dto){
        TransactionResponseDto transfer = service.transfer(dto.fromAccount(), dto.toAccount(), dto.amount());
        return ResponseEntity.status(HttpStatus.CREATED).body(transfer);
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDto> deposit(@RequestBody TransactionRequestDto dto){
        TransactionResponseDto deposit = service.deposit(dto.toAccountId(), dto.amount());
        return ResponseEntity.status(HttpStatus.CREATED).body(deposit);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponseDto> withdraw(@RequestBody TransactionRequestDto dto){
        TransactionResponseDto withdraw = service.withdraw(dto.fromAccountId(), dto.amount());
        return ResponseEntity.status(HttpStatus.CREATED).body(withdraw);
    }

    @PostMapping("/payment")
    public ResponseEntity<TransactionResponseDto> payment(@RequestBody TransactionRequestDto dto){
        TransactionResponseDto payment = service.payment(dto.fromAccountId(), dto.amount(), dto.description());
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @PatchMapping("/{transactionId}/canceled")
    public ResponseEntity<Void> cancel(@PathVariable("transactionId") UUID id){
        service.canceled(id);
        return ResponseEntity.ok().build();
    }

}
