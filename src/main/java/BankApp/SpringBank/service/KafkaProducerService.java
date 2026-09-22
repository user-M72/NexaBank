package BankApp.SpringBank.service;

import BankApp.SpringBank.event.AuthEvent;
import BankApp.SpringBank.event.CardEvent;
import BankApp.SpringBank.event.TransactionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TRANSACTION_TROPIC = "transaction_events";
    private static final String CARD_TROPIC = "card_events";
    private static final String AUTH_TROPIC = "auth_events";

    public void sendTransactionEvent(TransactionEvent event){
        kafkaTemplate.send(TRANSACTION_TROPIC, event.getTransactionId(), event);
        log.info("Transaction event sent: {} - {}", event.getType(), event.getTransactionId());
    }

    public void sendCardEvent(CardEvent event){
        kafkaTemplate.send(CARD_TROPIC, event.getCardId(), event);
        log.info("Card event send: {} - {}", event.getAction(), event.getCardId());
    }

    public void sendAuthEvent(AuthEvent event){
        kafkaTemplate.send(AUTH_TROPIC, event.getUserId(), event);
        log.info("Auth event send: {} - {}", event.getAction(), event.getUsername());
    }
}
