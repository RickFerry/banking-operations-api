package com.example.banking.controller;

import com.example.banking.model.Account;
import com.example.banking.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;

    @PostMapping("/event")
    public ResponseEntity<?> handleEvent(@RequestBody Map<String, Object> request) {
        String type = (String) request.get("type");
        String originId = (String) request.get("origin");
        String destinationId = (String) request.get("destination");
        int amount = ((Number) request.get("amount")).intValue();

        switch (type) {
            case "withdraw":
                Account withdrawnAccount = accountService.withdraw(originId, amount);
                if (withdrawnAccount == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("error", "Origin account not found or insufficient funds"));
                }
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Map.of("origin", withdrawnAccount));

            case "deposit":
                Account depositedAccount = accountService.deposit(originId, amount);
                if (depositedAccount == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("error", "Origin account not found"));
                }
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Map.of("origin", depositedAccount));

            case "transfer":
                boolean success = accountService.transfer(originId, destinationId, amount);
                if (!success) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("error", "Transfer failed"));
                }
                Account originAccount = accountService.findAccountById(originId);
                Account destinationAccount = accountService.findAccountById(destinationId);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Map.of("origin", originAccount, "destination", destinationAccount));

            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Invalid event type"));
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestParam String id) {
        Account account = accountService.findAccountById(id);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Account not found"));
        }
        return ResponseEntity.ok(account.getBalance());
    }
}
