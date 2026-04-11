package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.CardCreateDto;
import BankApp.SpringBank.dto.res.card.CardResponseDto;
import BankApp.SpringBank.model.Enum.CardType;
import BankApp.SpringBank.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/card/v1")
@AllArgsConstructor
public class CardApi {

    private final CardService service;

    @PostMapping("/create")
    public ResponseEntity<CardResponseDto> createCard(@RequestBody CardCreateDto dto){
        CardResponseDto card = service.createCard(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(card);
    }

    @GetMapping("/my")
    public ResponseEntity<List<CardResponseDto>> getMyCards() {
        List<CardResponseDto> myCards = service.getMyCards();
        return ResponseEntity.ok(myCards);
    }


    @PatchMapping("/{cardId}/block")
    public ResponseEntity<CardResponseDto> blockCard(@PathVariable("cardId") UUID id){
        CardResponseDto block = service.block(id);
        return ResponseEntity.ok(block);
    }

    @PatchMapping("/{cardId}/unBlock")
    public ResponseEntity<CardResponseDto> unBlockCard(@PathVariable("cardId") UUID id){
        CardResponseDto unBlock = service.unBlock(id);
        return ResponseEntity.ok(unBlock);
    }

    @GetMapping("/cardType")
    public CardType[] CardType(){
        return CardType.values();
    }
}
