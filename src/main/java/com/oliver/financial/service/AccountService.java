package com.oliver.financial.service;

import java.util.Optional;

import com.oliver.financial.domain.*;
import com.oliver.financial.repository.AccountRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    private final Logger log = LoggerFactory.getLogger(CardService.class);

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account updateAccount(Movement movement, Account account) {
        log.debug("Request to update Account {} with movement : {} ",account, movement);
        switch (movement.getType()) {
            case CASH_DEPOSITED:
                account.setBalance(account.getBalance() + movement.getAmount());
                break;
            case CASH_WITHDRAWN:
                account.setBalance(account.getBalance() - movement.getAmount());
                break;
            case TRANSFER_INCOME:
                account.setBalance(account.getBalance() + movement.getAmount());
                break;
            case TRANSFER_OUTGONE:
                account.setBalance(account.getBalance() - movement.getAmount());
                break;
            default:
                break;
        }
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Optional<Account> findOneByIban(String iban) {
        log.debug("Request to get Account with iban : {}", iban);
        return accountRepository.findOneByIban(iban);
    }
}
