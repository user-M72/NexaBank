package BankApp.SpringBank.repository;

import BankApp.SpringBank.model.Transaction;
import BankApp.SpringBank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByFromCardIdOrToCardId(UUID id, UUID id1);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.fromCard.account.owner = :user " +
            "OR t.toCard.account.owner = :user " +
            "ORDER BY t.createdDate DESC")
    List<Transaction> findAllByUser(@Param("user") User user);
}
