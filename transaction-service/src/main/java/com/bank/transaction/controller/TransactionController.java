package com.bank.transaction.controller;

import com.bank.transaction.entity.Transaction;
import com.bank.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    public Transaction create(@RequestBody Transaction transaction) {
        return service.createTransaction(transaction);
    }
}
