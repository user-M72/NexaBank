package BankApp.SpringBank.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthEvent {

    private String userId;
    private String username;
    private String email;
    private String action;

}
