package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.req.card.CardRequestDto;
import BankApp.SpringBank.dto.res.card.CardResponseDto;
import BankApp.SpringBank.exception.CardNotFoundException;
import BankApp.SpringBank.mapper.CardMapper;
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

    private final AccountService accountService;
    private final CardRepository cardRepository;
    private final CardMapper mapper;

    @Override
    public List<CardResponseDto> get() {
        return cardRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public CardResponseDto getById(UUID id) {
        return null;
    }

    @Override
    public CardResponseDto created(CardRequestDto dto) {
        return null;
    }

    @Override
    public CardResponseDto updated(UUID id, CardRequestDto dto) {
        return null;
    }

    @Override
    public void deleted(UUID id) {
        if (!cardRepository.existsById(id))
            throw new CardNotFoundException(id);
        cardRepository.deleteById(id);
    }
}
