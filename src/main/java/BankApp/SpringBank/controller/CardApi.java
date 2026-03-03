package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.res.card.CardResponseDto;
import BankApp.SpringBank.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card/v1")
public class CardApi {

    private final CardService service;

    @GetMapping
    public List<CardResponseDto> get(){
        return service.get();
    }
}
