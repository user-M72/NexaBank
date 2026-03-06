package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.req.account.AccountRequestDto;
import BankApp.SpringBank.dto.res.account.AccountResponseDto;
import BankApp.SpringBank.model.Enum.Currency;
import BankApp.SpringBank.service.AccountService;
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
public class AccountApi {

    private final AccountService service;

    @GetMapping
    public List<AccountResponseDto> get(){
        return service.get();
    }

    @GetMapping("/{accountId}")
    public AccountResponseDto getById(@PathVariable("accountId")UUID id){
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> created(@RequestBody AccountRequestDto dto){
        AccountResponseDto created = service.created(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{accountId}")
    public AccountResponseDto updated(@PathVariable("accountId") UUID id,
                                      @RequestBody AccountRequestDto dto){
        return service.updated(id, dto);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleted(@PathVariable("accountId") UUID id){
        service.deleted(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<AccountResponseDto> deposit(@PathVariable("accountId") UUID id,
                                                      @RequestBody BigDecimal bigDecimal){
        AccountResponseDto deposited = service.deposit(id, bigDecimal);
        return ResponseEntity.ok(deposited);
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<AccountResponseDto> withdraw(@PathVariable("accountId") UUID id,
                                                       @RequestBody BigDecimal bigDecimal){
        AccountResponseDto withdraw = service.withdraw(id, bigDecimal);
        return ResponseEntity.ok(withdraw);
    }

    @PatchMapping("/{accountId}/block")
    public ResponseEntity<Void> block(@PathVariable("accountId") UUID id){
        service.block(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{accountId}/unblock")
    public ResponseEntity<Void> unblock(@PathVariable("accountId") UUID id){
        service.unblock(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/currency")
    public Currency[] currencyType(){
        return Currency.values();
    }
}
