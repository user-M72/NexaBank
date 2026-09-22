package BankApp.SpringBank.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CardEvent {

    private String cardId;
    private String userId;
    private String cardType;
    private String action;

}
