package com.bank.notification.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "transaction-topic", groupId = "notification-group")
    public void consume(String message) {
        System.out.println("📩 [Kafka Consumer] Received: " + message);

        // Simulate sending a notification (like email or SMS)
        String notification = "🔔 Notification: Transaction successfully processed!";
        System.out.println(notification);
    }
}
