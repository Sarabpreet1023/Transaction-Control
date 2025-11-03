package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BankingService {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private TransactionRepository txnRepo;

    @Transactional
    public void transferMoney(String fromAccountNumber, String toAccountNumber, Double amount) {

        Account from = accountRepo.findByAccountNumber(fromAccountNumber);
        Account to = accountRepo.findByAccountNumber(toAccountNumber);

        if (from == null || to == null)
            throw new RuntimeException("Account not found");
        if (from.getBalance() < amount)
            throw new RuntimeException("Insufficient funds");

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        accountRepo.save(from);
        accountRepo.save(to);

        Transaction txn = new Transaction(from, to, amount, LocalDateTime.now(), "SUCCESS");
        txnRepo.save(txn);
    }
}
