package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.req.card.CardRequestDto;
import BankApp.SpringBank.dto.res.card.CardResponseDto;
import BankApp.SpringBank.exception.*;
import BankApp.SpringBank.mapper.CardMapper;
import BankApp.SpringBank.model.Account;
import BankApp.SpringBank.model.Card;
import BankApp.SpringBank.model.Enum.AccountType;
import BankApp.SpringBank.model.Enum.CardType;
import BankApp.SpringBank.repository.CardRepository;
import BankApp.SpringBank.service.impl.CardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @InjectMocks
    CardServiceImpl testService;

    @Mock
    private AccountService accountService;
    @Mock
    private CardRepository repository;
    @Mock
    private CardMapper mapper;

    private final UUID cardId = UUID.randomUUID();
    private final UUID accountId = UUID.randomUUID();

    private CardResponseDto response;
    private CardRequestDto request;
    private CardRequestDto creditRequest;
    private CardRequestDto virtualRequest;
    private Card card;
    private Account account;


    @BeforeEach
    void setup(){

        card = new Card();
        account = new Account();

        response = new CardResponseDto(
                cardId,
                "cardNumber",
                "name",
                "",
                CardType.DEBIT,
                BigDecimal.valueOf(1000),
                true
        );

        request = new CardRequestDto(
                "name",
                "",
                CardType.DEBIT,
                BigDecimal.valueOf(1000),
                accountId
        );

        creditRequest = new CardRequestDto(
                "name",
                "",
                CardType.CREDIT,
                BigDecimal.valueOf(1000),
                accountId
        );

        virtualRequest = new CardRequestDto(
                "name",
                "",
                CardType.VIRTUAL,
                BigDecimal.valueOf(1000),
                accountId
        );
    }

    @Test
    void get_shouldWork(){

        when(repository.findAll()).thenReturn(List.of(card));
        when(mapper.toDto(card)).thenReturn(response);

        List<CardResponseDto> expected = List.of(response);
        List<CardResponseDto> actual = testService.get();

        assertEquals(expected, actual);

        verify(repository).findAll();
        verify(mapper).toDto(card);
    }

    @Test
    void getById_shouldWork(){

        when(repository.findById(cardId)).thenReturn(Optional.of(card));
        when(mapper.toDto(card)).thenReturn(response);

        CardResponseDto actual = testService.getById(cardId);

        assertEquals(response, actual);

        verify(repository).findById(cardId);
        verify(mapper).toDto(card);

    }

    @Test
    void getById_shouldThrow_whenCardNotFound(){

        when(repository.findById(cardId)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class,
                ()-> testService.getById(cardId));

        verify(repository).findById(cardId);
        verify(mapper, never()).toDto(any());

    }

    @Test
    void create_debit_shouldWork() {
        account.setType(AccountType.CHECKING); // ← DEBIT только для CHECKING

        when(accountService.findById(request.accountId())).thenReturn(account);
        when(mapper.toEntity(eq(request), eq(account), any(String.class), any(String.class))).thenReturn(card);
        when(repository.save(card)).thenReturn(card);
        when(mapper.toDto(card)).thenReturn(response);

        CardResponseDto actual = testService.create(request);

        assertEquals(response, actual);
        verify(accountService).findById(request.accountId());
        verify(mapper).toEntity(eq(request), eq(account), any(String.class), any(String.class));
        verify(repository).save(card);
        verify(mapper).toDto(card);
    }

    @Test
    void create_debit_shouldThrow(){
        account.setType(AccountType.SAVINGS);

        when(accountService.findById(request.accountId())).thenReturn(account);

        assertThrows(InvalidCardTypeException.class,
                ()-> testService.create(request));

        verify(repository, never()).save(any());
    }

    @Test
    void create_credit_shouldWork() {
        account.setType(AccountType.CREDIT);

        when(accountService.findById(creditRequest.accountId())).thenReturn(account);
        when(mapper.toEntity(eq(creditRequest), eq(account), any(String.class), any(String.class))).thenReturn(card);
        when(repository.save(card)).thenReturn(card);
        when(mapper.toDto(card)).thenReturn(response);

        CardResponseDto actual = testService.create(creditRequest);

        assertEquals(response, actual);
        verify(repository).save(card);
    }

    @Test
    void create_credit_shouldThrow_whenNotCreditAccount() {
        account.setType(AccountType.CHECKING);

        when(accountService.findById(creditRequest.accountId())).thenReturn(account);

        assertThrows(InvalidCardTypeException.class,
                () -> testService.create(creditRequest));

        verify(repository, never()).save(any());
    }

    @Test
    void create_virtual_shouldWork(){
        account.setType(AccountType.SAVINGS);

        when(accountService.findById(virtualRequest.accountId())).thenReturn(account);
        when(mapper.toEntity(eq(virtualRequest), eq(account), any(String.class), any(String.class))).thenReturn(card);
        when(repository.save(card)).thenReturn(card);
        when(mapper.toDto(card)).thenReturn(response);

        CardResponseDto actual = testService.create(virtualRequest);

        assertEquals(response, actual);

        verify(accountService).findById(virtualRequest.accountId());
        verify(mapper).toEntity(eq(virtualRequest), eq(account), any(String.class), any(String.class));
        verify(repository).save(card);
        verify(mapper).toDto(card);
    }

    @Test
    void create_virtual_shouldThrow(){
        when(accountService.findById(request.accountId()))
                .thenThrow(new AccountNotFoundException(request.accountId()));


        assertThrows(AccountNotFoundException.class,
                ()-> testService.create(request));

        verify(repository, never()).findById(any());
    }

    @Test
    void update_shouldWork(){

        when(repository.findById(cardId)).thenReturn(Optional.of(card));
        when(repository.save(card)).thenReturn(card);
        when(mapper.toDto(card)).thenReturn(response);

        CardResponseDto actual = testService.update(cardId, request);

        assertEquals(response, actual);

        verify(repository).findById(cardId);
        verify(repository).save(card);
        verify(mapper).toDto(card);

    }

    @Test
    void update_shouldThrow(){
        when(repository.findById(cardId)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class,
                ()-> testService.update(cardId, request));

        verify(repository).findById(cardId);
        verify(mapper, never()).updateFromDto(any(), any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toDto(any());

    }

    @Test
    void delete_shouldWork(){

        when(repository.existsById(cardId)).thenReturn(true);

        testService.delete(cardId);
        verify(repository).existsById(cardId);
        verify(repository).deleteById(cardId);
    }

    @Test
    void delete_shouldThrow(){

        when(repository.existsById(cardId)).thenReturn(false);

        assertThrows(CardNotFoundException.class,
                ()-> testService.delete(cardId));

        verify(repository).existsById(cardId);
    }

    @Test
    void block_shouldWork(){
        card.setActive(true);

        when(repository.findById(cardId)).thenReturn(Optional.of(card));
        when(repository.save(card)).thenReturn(card);

        testService.block(cardId);

        assertFalse(card.isActive());
        verify(repository).findById(cardId);
        verify(repository).save(card);

    }

    @Test
    void block_shouldThrow(){
        card.setActive(false);

        when(repository.findById(cardId)).thenReturn(Optional.of(card));

        assertThrows(CardBlockedException.class,
                ()-> testService.block(cardId));

        verify(repository, never()).save(any());

    }

    @Test
    void block_shouldThrow_whenCardNotFound(){
        when(repository.findById(cardId)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class,
                ()-> testService.block(cardId));

        verify(repository, never()).save(any());
    }

    @Test
    void unblock_shouldWork(){
        card.setActive(false);

        when(repository.findById(cardId)).thenReturn(Optional.of(card));
        when(repository.save(card)).thenReturn(card);

        testService.unblock(cardId);

        assertTrue(card.isActive());
        verify(repository).findById(cardId);
        verify(repository).save(card);
    }

    @Test
    void unblock_shouldThrow(){
        card.setActive(true);

        when(repository.findById(cardId)).thenReturn(Optional.of(card));

        assertThrows(CardNotBlockedException.class,
                ()-> testService.unblock(cardId));

        verify(repository, never()).save(any());
    }

    @Test
    void unblock_shouldThrow_whenCardNotFound(){
        when(repository.findById(cardId)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class,
                ()-> testService.unblock(cardId));

        verify(repository, never()).save(any());
    }
}
