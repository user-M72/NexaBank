package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.req.card.CardRequestDto;
import BankApp.SpringBank.dto.res.card.CardResponseDto;
import BankApp.SpringBank.exception.CardBlockedException;
import BankApp.SpringBank.exception.CardNotBlockedException;
import BankApp.SpringBank.exception.CardNotFoundException;
import BankApp.SpringBank.exception.InvalidCardTypeException;
import BankApp.SpringBank.mapper.CardMapper;
import BankApp.SpringBank.model.Account;
import BankApp.SpringBank.model.Card;
import BankApp.SpringBank.model.Enum.AccountType;
import BankApp.SpringBank.repository.CardRepository;
import BankApp.SpringBank.service.AccountService;
import BankApp.SpringBank.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    @Override
    public List<CardResponseDto> get() {
        return List.of();
    }

    @Override
    public CardResponseDto getById(UUID id) {
        return null;
    }

    @Override
    public CardResponseDto create(CardRequestDto dto) {
        return null;
    }

    @Override
    public CardResponseDto update(UUID id, CardRequestDto dto) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
