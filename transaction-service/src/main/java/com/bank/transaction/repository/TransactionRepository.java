package com.bank.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bank.transaction.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}

