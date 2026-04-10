package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.CardCreateDto;
import BankApp.SpringBank.dto.req.card.CardRequestDto;
import BankApp.SpringBank.dto.res.card.CardResponseDto;

import java.util.List;
import java.util.UUID;

public interface CardService {

    CardResponseDto createCard(CardCreateDto dto);

    List<CardResponseDto> getMyCards();

    List<CardResponseDto> getCardsByAccount(UUID accountId);

    CardResponseDto blockCard(UUID cardId);

}
