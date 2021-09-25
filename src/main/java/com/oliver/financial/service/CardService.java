package com.oliver.financial.service;

import java.util.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oliver.financial.domain.Card;
import com.oliver.financial.domain.Movement;
import com.oliver.financial.repository.CardRepository;

import org.slf4j.*;

@Service
public class CardService {

    private final Logger log = LoggerFactory.getLogger(CardService.class);

    private final CardRepository cardRepository;

    private final PasswordEncoder passwordEncoder;

    public CardService(CardRepository cardRepository, PasswordEncoder passwordEncoder) {
        this.cardRepository = cardRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Card save(Card card) {
        log.debug("Request to save Card : {}", card);
        return cardRepository.save(card);
    }

    @Transactional(readOnly = true)
    public Optional<Card> findOneByNumber(Integer number) {
        log.debug("Request to get Card with number : {}", number);
        return cardRepository.findOneByNumber(number);
    }

    public Card changePin(Card card, String newPin) {
        card.setPin(passwordEncoder.encode(newPin));
        return save(card);
    }

    public Boolean checkCardPin(String currentClearTextPin, String currentEncryptedPin) {
        return passwordEncoder.matches(currentClearTextPin, currentEncryptedPin);
    }

    public Card updateCard(Movement movement, Card card) {
        log.debug("Request to update Card {} with movement : {} ", card, movement);
        switch (movement.getType()) {
            case CASH_DEPOSITED:
                card.setBalanceAvailable(card.getBalanceAvailable() + movement.getAmount());
                break;
            case CASH_WITHDRAWN:
                card.setBalanceAvailable(card.getBalanceAvailable() - movement.getAmount());
                break;
            case TRANSFER_INCOME:
                card.setBalanceAvailable(card.getBalanceAvailable() + movement.getAmount());
                break;
            case TRANSFER_OUTGONE:
                card.setBalanceAvailable(card.getBalanceAvailable() - movement.getAmount());
                break;
            default:
                break;
        }
        return cardRepository.save(card);
    }
}
