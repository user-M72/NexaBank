package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.CardCreateDto;
import BankApp.SpringBank.dto.res.card.CardResponseDto;
import BankApp.SpringBank.mapper.CardMapper;
import BankApp.SpringBank.model.Account;
import BankApp.SpringBank.model.Card;
import BankApp.SpringBank.model.Enum.AccountStatus;
import BankApp.SpringBank.repository.CardRepository;
import BankApp.SpringBank.service.AccountService;
import BankApp.SpringBank.service.AuthService;
import BankApp.SpringBank.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository repository;
    private final AccountService accountService;
    private final CardMapper mapper;
    private final AuthService authService;

    @Override
    public CardResponseDto createCard(CardCreateDto dto) {
        Account account = accountService.findById(dto.accountId());

        if (account.getStatus() != AccountStatus.ACTIVE){
            throw new RuntimeException("Account is not active");
        }

        Card card = Card.builder()
                .cardNumber(generateCardNumber())
                .cardHolderName(dto.cardHolderName())
                .expiryDate(generateExpiryDate())
                .cvvHash(hashCvv(generateCvv()))
                .cardType(dto.cardType())
                .dailyLimit(dto.dailyLimit())
                .isActive(true)
                .account(account)
                .build();

        Card save = repository.save(card);
        return mapper.toDto(save);
    }

    @Override
    public List<CardResponseDto> getMyCards() {
        return repository.findAllByAccount_Owner(authService.getCurrentUser())
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CardResponseDto> getCardsByAccount(UUID accountId) {
        Account account = accountService.findById(accountId);
        return repository.findAllByAccount(account)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public CardResponseDto blockCard(UUID cardId) {
        Card card = repository.findById(cardId)
                .orElseThrow(()-> new RuntimeException("Card not found by ID: " + cardId));

        card.setActive(false);
        Card save = repository.save(card);
        return mapper.toDto(save);
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

    private String generateCvv() {
        return String.format("%03d", (int)(Math.random() * 1000));
    }

    private String hashCvv(String cvv) {
        return BCrypt.hashpw(cvv, BCrypt.gensalt());
    }

}
