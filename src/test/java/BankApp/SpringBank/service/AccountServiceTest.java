package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.req.account.AccountRequestDto;
import BankApp.SpringBank.dto.res.account.AccountResponseDto;
import BankApp.SpringBank.exception.*;
import BankApp.SpringBank.mapper.AccountMapper;
import BankApp.SpringBank.model.Account;
import BankApp.SpringBank.model.Enum.AccountType;
import BankApp.SpringBank.model.Enum.Currency;
import BankApp.SpringBank.model.User;
import BankApp.SpringBank.repository.AccountRepository;
import BankApp.SpringBank.repository.UserRepository;
import BankApp.SpringBank.service.impl.AccountServiceImpl;
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
public class AccountServiceTest {

    @InjectMocks
    AccountServiceImpl testService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserService userService;
    @Mock
    private AccountMapper mapper;
    @Mock
    private UserRepository userRepository;

    private AccountResponseDto response;
    private AccountRequestDto request;
    private Account account;
    private User user;

    private final UUID accountId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();

    private AccountType type;
    private Currency currency;
    private BigDecimal bigDecimal;

    @BeforeEach
    void setup() {

        account = new Account();
        user = new User();

        response = new AccountResponseDto(
                accountId,
                "123",
                bigDecimal,
                type,
                currency,
                false
        );

        request = new AccountRequestDto(
                type,
                currency,
                userId

        );
    }

    @Test
    void get_shouldWork() {

        List<Account> accounts = List.of(account);

        when(accountRepository.findAll()).thenReturn(accounts);
        when(mapper.toDto(account)).thenReturn(response);

        List<AccountResponseDto> expected = List.of(response);

        List<AccountResponseDto> actual = testService.get();

        assertEquals(expected, actual);

        verify(accountRepository).findAll();
        verify(mapper).toDto(account);
    }

    @Test
    void getById_shouldWork() {

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(mapper.toDto(account)).thenReturn(response);

        AccountResponseDto actual = testService.getById(accountId);

        assertEquals(response, actual);

        verify(accountRepository).findById(accountId);
        verify(mapper).toDto(account);
    }

    @Test
    void getById_shouldThrow_accountNotFound() {

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,
                () -> testService.getById(accountId));

        verify(accountRepository).findById(accountId);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void create_shouldWork() {

        when(userService.findById(userId)).thenReturn(user);
        when(mapper.toEntity(eq(request), eq(user), any(String.class))).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(mapper.toDto(account)).thenReturn(response);

        AccountResponseDto actual = testService.create(request);

        assertEquals(response, actual);

        verify(userService).findById(userId);
        verify(mapper).toEntity(eq(request), eq(user), any(String.class));
        verify(accountRepository).save(account);
        verify(mapper).toDto(account);
    }

    @Test
    void create_shouldThrow_userNotFound(){

        when(userService.findById(userId)).thenThrow(new UserNotFoundException(request.userId()));

        assertThrows(UserNotFoundException.class,
                ()-> testService.create(request));

        verify(userService).findById(userId);
        verify(accountRepository, never()).save(any());
        verify(mapper, never()).toEntity(any(), any(), any());

    }

    @Test
    void update_shouldWork() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(mapper.toDto(account)).thenReturn(response);

        AccountResponseDto actual = testService.update(accountId, request);

        assertEquals(response, actual);

        verify(accountRepository).findById(accountId);
        verify(mapper).updateFromDto(request, account);
        verify(accountRepository).save(account);
        verify(mapper).toDto(account);
    }

    @Test
    void update_shouldThrow_userNotFound(){

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,
                ()-> testService.update(accountId, request));

        verify(accountRepository).findById(accountId);
        verify(mapper, never()).updateFromDto(any(), any());
        verify(accountRepository, never()).save(any());
        verify(mapper, never()).toDto(any());

    }

    @Test
    void delete_shouldWork(){
        when(accountRepository.existsById(accountId)).thenReturn(true);

        testService.delete(accountId);

        verify(accountRepository).existsById(accountId);
        verify(accountRepository).deleteById(accountId);
    }

    @Test
    void delete_shouldThrow(){

        when(accountRepository.existsById(accountId)).thenReturn(false);

        assertThrows(AccountNotFoundException.class,
                ()-> testService.delete(accountId));

        verify(accountRepository).existsById(accountId);
    }

    @Test
    void deposit_checking_shouldWork(){

        account.setType(AccountType.CHECKING);
        account.setBalance(BigDecimal.valueOf(500));
        account.setBlocked(false);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(mapper.toDto(account)).thenReturn(response);

        AccountResponseDto actual = testService.deposit(accountId, BigDecimal.valueOf(100));

        assertEquals(response, actual);
        assertEquals(BigDecimal.valueOf(600), account.getBalance());
        verify(accountRepository).save(account);

    }

    @Test
    void deposit_saving_shouldWork(){

        account.setType(AccountType.SAVINGS);
        account.setBalance(BigDecimal.valueOf(500));
        account.setBlocked(false);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(mapper.toDto(account)).thenReturn(response);

        AccountResponseDto actual = testService.deposit(accountId, BigDecimal.valueOf(200));

        assertEquals(response, actual);
        assertEquals(BigDecimal.valueOf(700), account.getBalance());

        verify(accountRepository).save(account);
    }

    @Test
    void deposit_savings_shouldThrow_whenAmountLessThan100(){

        account.setType(AccountType.SAVINGS);
        account.setBlocked(false);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(InvalidAmountException.class,
                ()-> testService.deposit(accountId, BigDecimal.valueOf(50)));

        verify(accountRepository, never()).save(any());
    }

    @Test
    void deposit_credit_shouldWork(){

        account.setType(AccountType.CREDIT);
        account.setBalance(BigDecimal.valueOf(-500));
        account.setBlocked(false);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(mapper.toDto(account)).thenReturn(response);

        AccountResponseDto actual = testService.deposit(accountId, BigDecimal.valueOf(200));

        assertEquals(response, actual);
        assertEquals(BigDecimal.valueOf(-300), account.getBalance());
    }

    @Test
    void deposit_credit_shouldThrow_whnOverDebt(){

        account.setType(AccountType.CREDIT);
        account.setBalance(BigDecimal.valueOf(-100));
        account.setBlocked(false);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(InvalidAmountException.class,
                ()-> testService.deposit(accountId, BigDecimal.valueOf(200)));

        verify(accountRepository, never()).save(any());
    }

    @Test
    void deposit_shouldThrow_whenAccountBlocked(){
        account.setBlocked(true);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(AccountBlockedException.class,
                ()-> testService.deposit(accountId, BigDecimal.valueOf(100)));

        verify(accountRepository, never()).save(any());
    }

    @Test
    void withdraw_checking_shouldWork(){
        account.setType(AccountType.CHECKING);
        account.setBalance(BigDecimal.valueOf(500));
        account.setBlocked(false);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(mapper.toDto(account)).thenReturn(response);

        AccountResponseDto actual = testService.withdraw(accountId, BigDecimal.valueOf(400));

        assertEquals(response, actual);
        assertEquals(BigDecimal.valueOf(100), account.getBalance());

        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(account);
        verify(mapper).toDto(account);

    }

    @Test
    void withdraw_checking_shouldThrow_whenInsufficientFunds(){

        account.setType(AccountType.CHECKING);
        account.setBalance(BigDecimal.valueOf(100));
        account.setBlocked(false);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(InsufficientFundsException.class,
                ()-> testService.withdraw(accountId, BigDecimal.valueOf(500)));

        verify(accountRepository, never()).save(any());

    }

    @Test
    void withdraw_saving_shouldWork(){

        account.setType(AccountType.SAVINGS);
        account.setBalance(BigDecimal.valueOf(200));
        account.setBlocked(false);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(mapper.toDto(account)).thenReturn(response);

        AccountResponseDto actual = testService.withdraw(accountId, BigDecimal.valueOf(100));

        assertEquals(response, actual);
        assertEquals(BigDecimal.valueOf(100), account.getBalance());
    }

    @Test
    void withdraw_saving_shouldThrow_InsufficientFunds(){

        account.setType(AccountType.SAVINGS);
        account.setBalance(BigDecimal.valueOf(500));
        account.setBlocked(false);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(InsufficientFundsException.class,
                ()-> testService.withdraw(accountId, BigDecimal.valueOf(300)));

        verify(accountRepository, never()).save(any());
    }

    @Test
    void withdraw_credit_shouldWork(){

        account.setType(AccountType.CREDIT);
        account.setBalance(BigDecimal.valueOf(-500));
        account.setBlocked(false);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(mapper.toDto(account)).thenReturn(response);

        AccountResponseDto actual = testService.withdraw(accountId, BigDecimal.valueOf(100));

        assertEquals(response, actual);
        assertEquals(BigDecimal.valueOf(-600), account.getBalance());

    }

    @Test
    void withdraw_credit_shouldThrow_InsufficientFunds(){
        account.setType(AccountType.CREDIT);
        account.setBalance(BigDecimal.valueOf(-9900));
        account.setBlocked(false);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(InsufficientFundsException.class,
                ()-> testService.withdraw(accountId, BigDecimal.valueOf(200)));

        verify(accountRepository, never()).save(any());
    }

    @Test
    void withdraw_shouldThrow_whenAccountBlocked() {
        account.setBlocked(true);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(AccountBlockedException.class,
                () -> testService.withdraw(accountId, BigDecimal.valueOf(100)));

        verify(accountRepository, never()).save(any());
    }

    @Test
    void block_shouldWork(){
        account.setBlocked(false);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        testService.block(accountId);

        assertTrue(account.isBlocked());
        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(account);
    }

    @Test
    void block_shouldThrow_AccountBlocked(){
        account.setBlocked(true);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(AccountBlockedException.class,
                ()-> testService.block(accountId));

        verify(accountRepository, never()).save(any());
    }

    @Test
    void block_shouldThrow_whenAccountNotFound(){

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,
                ()-> testService.block(accountId));

        verify(accountRepository, never()).save(any());
    }

    @Test
    void unblock_shouldWork(){
        account.setBlocked(true);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        testService.unblock(accountId);
        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(account);
    }

    @Test
    void unblock_shouldThrow(){
        account.setBlocked(false);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(AccountNotBlockedException.class,
                ()-> testService.unblock(accountId));

        verify(accountRepository, never()).save(any());
    }
}