package BankApp.SpringBank.model;

import BankApp.SpringBank.model.Enum.AccountType;
import BankApp.SpringBank.model.Enum.Currency;
import BankApp.SpringBank.model.baseDomain.BaseDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account extends BaseDomain<UUID> {

    private String accountNumber;
    private BigDecimal balance = BigDecimal.ZERO;
    private AccountType type;
    private Currency currency;
    private boolean isBlocked = false;

}
