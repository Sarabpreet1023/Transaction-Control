package com.example.bank.controller;

import com.example.bank.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankingController {

    @Autowired
    private BankingService bankingService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam String fromAccount,
                                           @RequestParam String toAccount,
                                           @RequestParam Double amount) {
        try {
            bankingService.transferMoney(fromAccount, toAccount, amount);
            return ResponseEntity.ok("Transfer successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
