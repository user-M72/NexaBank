package BankApp.SpringBank.repository;

import BankApp.SpringBank.model.Account;
import BankApp.SpringBank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByFromAccountOrToAccount(Account fromAccount, Account toAccount);
}
