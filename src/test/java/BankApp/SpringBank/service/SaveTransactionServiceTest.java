package BankApp.SpringBank.service;

import BankApp.SpringBank.model.Enum.TransactionStatus;
import BankApp.SpringBank.model.Transaction;
import BankApp.SpringBank.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SaveTransactionServiceTest {

    @InjectMocks
    SaveTransactionService testService;

    @Mock
    private TransactionRepository repository;

    @Test
    void saveTransaction_shouldWork(){
        Transaction transaction = new Transaction();
        transaction.setStatus(TransactionStatus.PENDING);

        testService.saveTransaction(transaction);

        assertEquals(TransactionStatus.FAILED, transaction.getStatus());
        verify(repository).save(transaction);

    }
}
