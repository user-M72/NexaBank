package BankApp.SpringBank.model;

import BankApp.SpringBank.model.Enum.CardType;
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

    @Column(name = "card_number", nullable = false, unique = true, length = 19)
    private String cardNumber;                  // 4444-4444-4444-4444

    @Column(name = "card_holder_name", nullable = false, length = 60)
    private String cardHolderName;

    @Column(name = "expiry_date", nullable = false, length = 7)
    private String expiryDate;                  // MM/YYYY

    @Column(name = "cvv_hash", nullable = false, length = 60)
    private String cvvHash;

    @Column(name = "card_type", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(name = "daily_limit", nullable = false, precision = 19, scale = 2)
    private BigDecimal dailyLimit;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
