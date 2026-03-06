package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.req.card.CardRequestDto;
import BankApp.SpringBank.dto.res.card.CardResponseDto;
import BankApp.SpringBank.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card/v1")
public class CardApi {

    private final CardService service;

    @GetMapping
    public List<CardResponseDto> get(){
        return service.get();
    }

    @GetMapping("/{carId}")
    public CardResponseDto getById(@PathVariable("carId")UUID id){
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<CardResponseDto> created(@RequestBody CardRequestDto dto){
        CardResponseDto created = service.created(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{carId}")
    public CardResponseDto updated(@PathVariable("carId") UUID id,
                                   @RequestBody CardRequestDto dto){
        return service.updated(id, dto);
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleted(@PathVariable("carId") UUID id){
        service.deleted(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{carId}/block")
    public ResponseEntity<Void> block(@PathVariable("carId") UUID id){
        service.block(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{carId}/unblock")
    public ResponseEntity<Void> unblock(@PathVariable("carId") UUID id){
        service.unblock(id);
        return ResponseEntity.ok().build();
    }

}
