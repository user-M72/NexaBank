package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.req.transaction.TransactionRequestDto;
import BankApp.SpringBank.dto.req.transfer.TransferRequestDto;
import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;
import BankApp.SpringBank.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction/v1")
@Tag(name = "Transaction API", description = "API for managing transaction in the banking application")
public class TransactionApi {

    private final TransactionService service;

    @Operation(summary = "Get all transactions", description = "Retrieve a list of all transactions in the system")
    @GetMapping
    public List<TransactionResponseDto> get(){
        return service.get();
    }

    @Operation(summary = "Get by id transactions", description = "Get all transactions in the system by id")
    @GetMapping("/{transactionId}")
    public TransactionResponseDto getById(@PathVariable("transactionId") UUID id){
        return service.getById(id);
    }

    @Operation(summary = "Create a transaction", description = "Create a new transaction in the system with the provided details")
    @PostMapping
    public ResponseEntity<TransactionResponseDto> created(@RequestBody TransactionRequestDto dto){
        TransactionResponseDto created = service.created(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Get by account id transactions", description = "Get all transactions in the system by account id")
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponseDto>> getByAccountId(@PathVariable("accountId") UUID id){
        List<TransactionResponseDto> accountId = service.getByAccountId(id);
        return ResponseEntity.ok(accountId);
    }

    @Operation(summary = "Transfer a transaction", description = "Transfer a new transaction in the system with the provided details")
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDto> transfer(@RequestBody TransferRequestDto dto){
        TransactionResponseDto transfer = service.transfer(dto.fromAccount(), dto.toAccount(), dto.amount());
        return ResponseEntity.status(HttpStatus.CREATED).body(transfer);
    }

    @Operation(summary = "Deposit a transaction", description = "Deposit a new transaction in the system with the provided details")
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDto> deposit(@RequestBody TransactionRequestDto dto){
        TransactionResponseDto deposit = service.deposit(dto.toAccountId(), dto.amount());
        return ResponseEntity.status(HttpStatus.CREATED).body(deposit);
    }

    @Operation(summary = "Withdraw a transaction", description = "Withdraw a new transaction in the system with the provided details")
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponseDto> withdraw(@RequestBody TransactionRequestDto dto){
        TransactionResponseDto withdraw = service.withdraw(dto.fromAccountId(), dto.amount());
        return ResponseEntity.status(HttpStatus.CREATED).body(withdraw);
    }

    @Operation(summary = "Payment a transaction", description = "Payment a new transaction in the system with the provided details")
    @PostMapping("/payment")
    public ResponseEntity<TransactionResponseDto> payment(@RequestBody TransactionRequestDto dto){
        TransactionResponseDto payment = service.payment(dto.fromAccountId(), dto.amount(), dto.description());
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @Operation(summary = "Cancel a transaction", description = "Cancel an existing transaction in the system by id")
    @PatchMapping("/{transactionId}/canceled")
    public ResponseEntity<Void> cancel(@PathVariable("transactionId") UUID id){
        service.canceled(id);
        return ResponseEntity.ok().build();
    }

}
