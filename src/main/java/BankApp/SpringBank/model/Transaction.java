package BankApp.SpringBank.model;

import BankApp.SpringBank.model.Enum.TransactionStatus;
import BankApp.SpringBank.model.Enum.TransactionType;
import BankApp.SpringBank.model.baseDomain.BaseDomain;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseDomain<UUID> {

}
