package BankApp.SpringBank.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class TransactionEvent {

    private String transactionId;
    private String cardId;
    private String userId;
    private String type;
    private BigDecimal amount;
    private String status;
    private LocalDateTime createdAt;

}
