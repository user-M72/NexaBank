package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.DepositRequestDto;
import BankApp.SpringBank.dto.req.transfer.TransferRequestDto;
import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;
import BankApp.SpringBank.mapper.TransactionMapper;
import BankApp.SpringBank.model.Card;
import BankApp.SpringBank.model.Enum.TransactionStatus;
import BankApp.SpringBank.model.Enum.TransactionType;
import BankApp.SpringBank.model.Transaction;
import BankApp.SpringBank.model.User;
import BankApp.SpringBank.repository.CardRepository;
import BankApp.SpringBank.repository.TransactionRepository;
import BankApp.SpringBank.service.AuthService;
import BankApp.SpringBank.service.CardService;
import BankApp.SpringBank.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final TransactionMapper mapper;
    private final AuthService authService;
    private final CardRepository cardRepository;
    private final CardService cardService;

    @Override
    @Transactional
    public TransactionResponseDto transfer(TransferRequestDto dto) {
        User user = authService.getCurrentUser();

        Card fromCard = cardService.findCardId(dto.fromCardId());
        Card toCard = cardService.findCardId(dto.toCardId());

        if (!fromCard.getAccount().getOwner().getId().equals(user.getId())){
            throw new RuntimeException("The card does not belong to the current user");
        }

        validateCard(fromCard);
        validateCard(toCard);

        if (fromCard.getBalance().compareTo(dto.amount()) < 0) {
            throw new RuntimeException("Insufficient funds on the card");
        }

        fromCard.setBalance(fromCard.getBalance().subtract(dto.amount()));
        toCard.setBalance(toCard.getBalance().add(dto.amount()));

        Transaction transaction = Transaction.builder()
                .amount(dto.amount())
                .type(TransactionType.TRANSFER)
                .status(TransactionStatus.SUCCESS)
                .description(dto.description())
                .fromCard(fromCard)
                .toCard(toCard)
                .build();
        Transaction saved = repository.save(transaction);
        return mapper.toDto(saved);

    }

    @Override
    @Transactional
    public TransactionResponseDto deposit(DepositRequestDto dto) {
        User user = authService.getCurrentUser();

        Card card = cardService.findCardId(dto.cardId());

        if (!card.getAccount().getOwner().getId().equals(user.getId())){
            throw new RuntimeException("The card does not belong to the current user");
        }

        validateCard(card);

        card.setBalance(card.getBalance().add(dto.amount()));

        Transaction transaction = Transaction.builder()
                .amount(dto.amount())
                .type(TransactionType.DEPOSIT)
                .status(TransactionStatus.SUCCESS)
                .toCard(card)
                .build();

        Transaction saved = repository.save(transaction);
        return mapper.toDto(saved);

    }

    @Override
    @Transactional
    public TransactionResponseDto withdraw(DepositRequestDto dto) {
        Card card = cardService.findCardId(dto.cardId());
        validateCard(card);

        if (card.getBalance().compareTo(dto.amount()) < 0) {
            throw new RuntimeException("Insufficient funds on the card");
        }

        card.setBalance(card.getBalance().subtract(dto.amount()));

        Transaction transaction = Transaction.builder()
                .amount(dto.amount())
                .type(TransactionType.WITHDRAWAL)
                .status(TransactionStatus.SUCCESS)
                .fromCard(card)
                .build();

        Transaction saved = repository.save(transaction);

        return mapper.toDto(saved);
    }

    @Override
    public List<TransactionResponseDto> getHistory(UUID id) {
        return repository.
                findByFromCardIdOrToCardId(id, id)
                .stream()
                .map(mapper::toDto)
                .toList();

    }

    private void validateCard(Card card) {
        if (card.isBlocked()) {
            throw new RuntimeException("Карта заблокирована: " + card.getId());
        }
        if (card.getAccount().isBlocked()) {
            throw new RuntimeException("Аккаунт заблокирован: " + card.getAccount().getId());
        }
    }

    private String generateReference() {
        return "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
}
