package BankApp.SpringBank.repository;

import BankApp.SpringBank.model.Account;
import BankApp.SpringBank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findAllByOwner(User user);
}
