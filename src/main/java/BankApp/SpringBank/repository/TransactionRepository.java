package BankApp.SpringBank.repository;

import BankApp.SpringBank.dto.res.transaction.TransactionResponseDto;
import BankApp.SpringBank.model.Account;
import BankApp.SpringBank.model.Transaction;
import BankApp.SpringBank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByFromCardIdOrToCardId(UUID id, UUID id1);
}
