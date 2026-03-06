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
        Card card = findCardId(id);
        return mapper.toDto(card);
    }

    @Override
    public CardResponseDto created(CardRequestDto dto) {
        Account account = accountService.findById(dto.accountId());

        switch (dto.cardType()){
            case DEBIT -> {
                if (account.getType() != AccountType.CHECKING){
                    throw new InvalidCardTypeException("Debit card for current account only!");
                }
            }
            case CREDIT -> {
                if (account.getType() != AccountType.CREDIT){
                    throw new InvalidCardTypeException("Кредитная карта только для кредитного счёта!");
                }
            }
            case VIRTUAL -> {
                /*The virtual card can be linked to any account*/
            }
        }
        String cardNumber = generateCardNumber();
        String cvvHash = generateCvv();

        Card card = mapper.toEntity(dto, account, cardNumber, cvvHash);
        Card saved = cardRepository.save(card);
        return mapper.toDto(saved);
    }

    @Override
    public CardResponseDto updated(UUID id, CardRequestDto dto) {
        Card card = findCardId(id);
        mapper.updateFromDto(dto, card);
        Card saved = cardRepository.save(card);
        return mapper.toDto(saved);
    }

    @Override
    public void deleted(UUID id) {
        if (!cardRepository.existsById(id))
            throw new CardNotFoundException(id);
        cardRepository.deleteById(id);
    }

    @Override
    public void block(UUID id) {
        Card card = findCardId(id);
        checkNotBlocked(card);
        card.setActive(false);
        cardRepository.save(card);
    }

    @Override
    public void unblock(UUID id) {
        Card card = findCardId(id);
        checkIsBlocked(card);
        card.setActive(true);
        cardRepository.save(card);
    }

    private Card findCardId(UUID id){
        return cardRepository.findById(id)
                .orElseThrow(()-> new CardNotFoundException(id));
    }

    private void checkNotBlocked(Card card){
        if (!card.isActive()){
            throw new CardBlockedException(card.getId());
        }
    }

    private void checkIsBlocked(Card card){
        if (card.isActive()){
            throw new CardNotBlockedException(card.getId());
        }
    }

    private String generateCardNumber(){
        return "4532" + String.format("%04d", (int)(Math.random() * 9999))
                + String.format("%04d", (int)(Math.random() * 9999))
                + String.format("%04d", (int)(Math.random() * 9999));
    }


    private String generateCvv(){
        return String.format("%03d", (int)(Math.random() * 999));
    }
}
