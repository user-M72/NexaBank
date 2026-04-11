package BankApp.SpringBank.model;

import BankApp.SpringBank.model.Enum.CardType;
import BankApp.SpringBank.model.Enum.Currency;
import BankApp.SpringBank.model.baseDomain.BaseDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Card extends BaseDomain<UUID> {

    private String cardNumber;
    private String expirationDate;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType type;


    private boolean blocked = false;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
