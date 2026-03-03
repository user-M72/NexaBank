package BankApp.SpringBank.model;

import BankApp.SpringBank.model.Enum.TransactionStatus;
import BankApp.SpringBank.model.Enum.TransactionType;
import BankApp.SpringBank.model.baseDomain.BaseDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction extends BaseDomain<UUID> {

    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;
    private String description;
    private String referenceNumber;

}
