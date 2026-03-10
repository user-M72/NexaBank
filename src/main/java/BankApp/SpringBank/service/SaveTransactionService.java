package BankApp.SpringBank.service;

import BankApp.SpringBank.model.Enum.TransactionStatus;
import BankApp.SpringBank.model.Transaction;
import BankApp.SpringBank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaveTransactionService {

    private final TransactionRepository repository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveTransaction(Transaction transaction){
        transaction.setStatus(TransactionStatus.FAILED);
        repository.save(transaction);
    }
}
