package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.CardCreateDto;
import BankApp.SpringBank.dto.res.card.CardResponseDto;
import BankApp.SpringBank.model.Card;

import java.util.List;
import java.util.UUID;

public interface CardService {

    CardResponseDto createCard(CardCreateDto dto);

    CardResponseDto block(UUID id);

    CardResponseDto unBlock(UUID id);

    List<CardResponseDto> get();

    List<CardResponseDto> getMyCards();

    Card findCardId(UUID id);
}
