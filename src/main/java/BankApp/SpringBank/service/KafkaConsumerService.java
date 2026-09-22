package BankApp.SpringBank.service;

import BankApp.SpringBank.event.AuthEvent;
import BankApp.SpringBank.event.CardEvent;
import BankApp.SpringBank.event.TransactionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(topics = "transaction-events", groupId = "nexabank-group")
    public void consumerTransactionEvent(TransactionEvent event){
        log.info("📩 Transaction received: type={}, amount={}, account={}",
                event.getType(), event.getAmount(), event.getCardId());
    }

    @KafkaListener(topics = "card-events", groupId = "nexabank-group")
    public void consumeCardEvent(CardEvent event) {
        log.info("💳 Card event received: action={}, cardId={}",
                event.getAction(), event.getCardId());

    }

    @KafkaListener(topics = "auth-events", groupId = "nexabank-group")
    public void consumeAuthEvent(AuthEvent event) {
        log.info("🔐 Auth event received: action={}, user={}",
                event.getAction(), event.getUsername());
    }


}
