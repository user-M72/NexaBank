package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.req.card.CardRequestDto;
import BankApp.SpringBank.dto.res.card.CardResponseDto;
import BankApp.SpringBank.service.CardService;
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
@RequestMapping("/api/card/v1")
@Tag(name = "Card API", description = "API for managing cards in the banking application")
public class CardApi {

    private final CardService service;

    @Operation(summary = "Get all cards", description = "Retrieve a list of all cards in the system")
    @GetMapping
    public List<CardResponseDto> get(){
        return service.get();
    }

    @Operation(summary = "Get by id cards", description = "Get all cards in the system by id")
    @GetMapping("/{carId}")
    public CardResponseDto getById(@PathVariable("carId")UUID id){
        return service.getById(id);
    }

    @Operation(summary = "Create a cards", description = "Create a new card in the system with the provided details")
    @PostMapping
    public ResponseEntity<CardResponseDto> created(@RequestBody CardRequestDto dto){
        CardResponseDto created = service.created(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update a cards", description = "Update an existing card in the system with the provided details")
    @PutMapping("/{carId}")
    public CardResponseDto updated(@PathVariable("carId") UUID id,
                                   @RequestBody CardRequestDto dto){
        return service.updated(id, dto);
    }

    @Operation(summary = "Delete a cards", description = "Delete an existing card in the system by id")
    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleted(@PathVariable("carId") UUID id){
        service.deleted(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Block a card", description = "Block an existing card in the system by id")
    @PatchMapping("/{carId}/block")
    public ResponseEntity<Void> block(@PathVariable("carId") UUID id){
        service.block(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Unblock a card", description = "Unblock an existing card in the system by id")
    @PatchMapping("/{carId}/unblock")
    public ResponseEntity<Void> unblock(@PathVariable("carId") UUID id){
        service.unblock(id);
        return ResponseEntity.ok().build();
    }

}
