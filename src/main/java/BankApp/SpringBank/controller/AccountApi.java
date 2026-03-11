package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.req.account.AccountRequestDto;
import BankApp.SpringBank.dto.res.account.AccountResponseDto;
import BankApp.SpringBank.model.Enum.AccountType;
import BankApp.SpringBank.model.Enum.Currency;
import BankApp.SpringBank.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/v1")
@Tag(name = "Account API", description = "API for managing accounts in the banking application")
public class AccountApi {

    private final AccountService service;

    @Operation(summary = "Get all accounts", description = "Retrieve a list of all accounts in the system")
    @GetMapping
    public List<AccountResponseDto> get(){
        return service.get();
    }

    @Operation(summary = "Get by id accounts", description = "Get all accounts in the system by id")
    @GetMapping("/{accountId}")
    public AccountResponseDto getById(@PathVariable("accountId")UUID id){
        return service.getById(id);
    }

    @Operation(summary = "Create a accounts", description = "Create a new account in the system with the provided details")
    @PostMapping
    public ResponseEntity<AccountResponseDto> created(@RequestBody AccountRequestDto dto){
        AccountResponseDto created = service.created(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update a accounts", description = "Update an existing account in the system with the provided details")
    @PutMapping("/{accountId}")
    public AccountResponseDto updated(@PathVariable("accountId") UUID id,
                                      @RequestBody AccountRequestDto dto){
        return service.updated(id, dto);
    }

    @Operation(summary = "Delete a accounts", description = "Delete an existing account in the system by id")
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleted(@PathVariable("accountId") UUID id){
        service.deleted(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deposit to account", description = "Deposit a specified amount to the account with the given ID")
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<AccountResponseDto> deposit(@PathVariable("accountId") UUID id,
                                                      @RequestBody BigDecimal bigDecimal){
        AccountResponseDto deposited = service.deposit(id, bigDecimal);
        return ResponseEntity.ok(deposited);
    }

    @Operation(summary = "Withdraw from account", description = "Withdraw a specified amount from the account with the given ID, ensuring sufficient funds are available")
    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<AccountResponseDto> withdraw(@PathVariable("accountId") UUID id,
                                                       @RequestBody BigDecimal bigDecimal){
        AccountResponseDto withdraw = service.withdraw(id, bigDecimal);
        return ResponseEntity.ok(withdraw);
    }

    @Operation(summary = "Block account", description = "Block the account with the given ID, preventing any transactions from being processed until it is unblocked")
    @PatchMapping("/{accountId}/block")
    public ResponseEntity<Void> block(@PathVariable("accountId") UUID id){
        service.block(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Unblock account", description = "Unblock the account with the given ID, allowing transactions to be processed again after being previously blocked")
    @PatchMapping("/{accountId}/unblock")
    public ResponseEntity<Void> unblock(@PathVariable("accountId") UUID id){
        service.unblock(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get currency types", description = "Retrieve a list of all available currency types in the system")
    @GetMapping("/currency")
    public Currency[] currencyType(){
        return Currency.values();
    }

    @Operation(summary = "Get account types", description = "Retrieve a list of all available account types in the system")
    @GetMapping("/type")
    public AccountType[] accountType(){
        return AccountType.values();
    }
}
