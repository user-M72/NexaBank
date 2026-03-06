package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.req.card.CardRequestDto;
import BankApp.SpringBank.dto.res.card.CardResponseDto;

import java.util.List;
import java.util.UUID;

public interface CardService {

    List<CardResponseDto> get();

    CardResponseDto getById(UUID id);

    CardResponseDto created(CardRequestDto dto);

    CardResponseDto updated(UUID id, CardRequestDto dto);

    void deleted(UUID id);

    void block(UUID id);

    void unblock(UUID id);
}
