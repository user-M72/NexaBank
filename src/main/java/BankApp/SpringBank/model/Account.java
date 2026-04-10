package BankApp.SpringBank.model;

import BankApp.SpringBank.model.Enum.AccountStatus;
import BankApp.SpringBank.model.Enum.AccountType;
import BankApp.SpringBank.model.Enum.Currency;
import BankApp.SpringBank.model.baseDomain.BaseDomain;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Account extends BaseDomain<UUID> {

    @Column(name = "bank_name", nullable = false, length = 30)
    private String bankName;

    @Column(name = "account_number", nullable = false, unique = true, length = 30)
    private String accountNumber;

    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "type", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name = "currency", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "status", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AccountStatus status = AccountStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;

    @OneToMany(mappedBy = "fromAccount")
    private List<Transaction> outgoingTransactions;

    @OneToMany(mappedBy = "toAccount")
    private List<Transaction> incomingTransactions;
}
