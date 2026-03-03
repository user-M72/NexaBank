package BankApp.SpringBank.model;

import BankApp.SpringBank.model.Enum.CardType;
import BankApp.SpringBank.model.baseDomain.BaseDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cards")
@Getter
@Setter
public class Card extends BaseDomain<UUID> {

    @Column(name = "cardNumber", nullable = false, length = 20)
    private String cardNumber;

    @Column(name = "cardHolderName", nullable = false, length = 10)
    private String cardHolderName;

    @Column(name = "expiryDate", nullable = false, length = 15)
    private String expiryDate;

    @Column(name = "cvvHash", nullable = false, length = 30)
    private String cvvHash;

    @Column(name = "cardType", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(name = "dailyLimit", nullable = false, length = 15)
    private BigDecimal dailyLimit;

    @Column(name = "isActive", nullable = false, length = 10)
    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
