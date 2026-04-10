package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.res.card.CardResponseDto;
import BankApp.SpringBank.model.Card;
import BankApp.SpringBank.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card/v1")
@AllArgsConstructor
public class CardApi {

    private final CardService service;

    @PostMapping("/create")
    public ResponseEntity<CardResponseDto> createCard(){
        return null;
    }

    @GetMapping("/my")
    public ResponseEntity<CardResponseDto> getMyCards() {
        return null;
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<CardResponseDto> getCardsByAccount(){
        return null;
    }

    @PatchMapping("/{cardId}/block")
    public ResponseEntity<CardResponseDto> blockCard(){
        return null;
    }
}
