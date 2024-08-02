package com.example.banking.service;

import com.example.banking.model.Account;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {

    private final Map<String, Account> accountMap = new HashMap<>();

    public AccountService() {
        accountMap.put("100", new Account("100", 100));
        accountMap.put("200", new Account("200", 200));
        accountMap.put("300", new Account("300", 300));
    }

    public Account findAccountById(String id) {
        return accountMap.get(id);
    }

    public Account withdraw(String id, int amount) {
        Account account = findAccountById(id);
        if (account != null && account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            return account;
        }
        return null;
    }

    public Account deposit(String id, int amount) {
        Account account = findAccountById(id);
        if (account != null) {
            account.setBalance(account.getBalance() + amount);
            return account;
        }
        return null;
    }

    public boolean transfer(String originId, String destinationId, int amount) {
        Account origin = withdraw(originId, amount);
        if (origin != null) {
            Account destination = deposit(destinationId, amount);
            return destination != null;
        }
        return false;
    }
}
