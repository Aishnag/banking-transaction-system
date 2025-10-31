package com.bank.transaction.service;

import com.bank.transaction.entity.Transaction;
import com.bank.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "transaction-topic";

    /**
     * Create and publish a new transaction event to Kafka
     */
    public Transaction createTransaction(Transaction transaction) {
        transaction.setTimestamp(LocalDateTime.now());
        Transaction saved = repository.save(transaction);

        // Construct readable Kafka event message
        String eventMessage = String.format(
                "Transaction Created -> Account: %s | Type: %s | Amount: %.2f | Time: %s",
                saved.getAccountNumber(),
                saved.getAccountType(),
                saved.getAmount(),
                saved.getTimestamp()
        );

        // Log and send event to Kafka
        System.out.println("✅ [Kafka Producer] Publishing event: " + eventMessage);
        kafkaTemplate.send(TOPIC, eventMessage);

        return saved;
    }

    /**
     * Retrieve transaction by ID with Redis caching enabled
     */
    @Cacheable(value = "transactions", key = "#id")
    public Transaction getTransactionById(Long id) {
        System.out.println("🔍 Fetching transaction from DB (not cache) for ID: " + id);
        Optional<Transaction> transaction = repository.findById(id);
        return transaction.orElse(null);
    }
}
