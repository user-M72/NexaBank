package BankApp.SpringBank.model;

import BankApp.SpringBank.model.Enum.CardType;
import BankApp.SpringBank.model.baseDomain.BaseDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cards")
@Getter
@Setter
public class Card extends BaseDomain<UUID> {

    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvvHash;

    private CardType cardType;

    private BigDecimal dailyLimit;
    private boolean isActive = true;
}
