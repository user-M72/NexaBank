package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.CardCreateDto;
import BankApp.SpringBank.dto.res.card.CardResponseDto;
import BankApp.SpringBank.exception.AccountBlockedException;
import BankApp.SpringBank.exception.CardNotFoundException;
import BankApp.SpringBank.exception.CardNumberNorFoundException;
import BankApp.SpringBank.mapper.CardMapper;
import BankApp.SpringBank.model.Account;
import BankApp.SpringBank.model.Card;
import BankApp.SpringBank.model.User;
import BankApp.SpringBank.repository.CardRepository;
import BankApp.SpringBank.service.AccountService;
import BankApp.SpringBank.service.AuthService;
import BankApp.SpringBank.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository repository;
    private final AccountService accountService;
    private final CardMapper mapper;
    private final AuthService authService;

    @Override
    public CardResponseDto createCard(CardCreateDto dto) {
        User user = authService.getCurrentUser();
        Account account = accountService.findById(dto.accountId());

        if (!account.getOwner().getId().equals(user.getId())){
            throw new AccountBlockedException(account.getOwner().getId());
        }

        if (account.isBlocked()) {
            throw new AccountBlockedException(account.getId());
        }

        Card card = Card.builder()
                .cardNumber(generateCardNumber())
                .expirationDate(generateExpiryDate())
                .balance(BigDecimal.ZERO)
                .currency(dto.currency())
                .type(dto.type())
                .blocked(false)
                .account(account)
                .build();

        Card saved = repository.save(card);
        return mapper.toDto(saved);
    }

    @Override
    public CardResponseDto block(UUID id) {
        Card card = findCardId(id);
        card.setBlocked(true);
        Card saved = repository.save(card);
        return  mapper.toDto(saved);
    }

    @Override
    public CardResponseDto unBlock(UUID id) {
        Card card = findCardId(id);
        card.setBlocked(false);
        Card saved = repository.save(card);
        return mapper.toDto(saved);
    }

    @Override
    public List<CardResponseDto> get() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<CardResponseDto> getMyCards() {
        User user = authService.getCurrentUser();
        return repository.findAllByAccount_Owner(user)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public Card findCardId(UUID id) {
        return repository.findById(id)
                .orElseThrow(()-> new CardNotFoundException(id));
    }

    @Override
    public Card findByCardNumber(String cardNumber) {
        return repository.findByCardNumber(cardNumber)
                .orElseThrow(()-> new CardNumberNorFoundException(cardNumber));
    }

    private String generateCardNumber() {
        return String.format("4444-%04d-%04d-%04d",
                (int)(Math.random() * 10000),
                (int)(Math.random() * 10000),
                (int)(Math.random() * 10000));
    }

    private String generateExpiryDate() {
        LocalDate expiry = LocalDate.now().plusYears(3);
        return String.format("%02d/%d", expiry.getMonthValue(), expiry.getYear());
    }
}
