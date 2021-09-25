package com.oliver.financial.controller;

import java.util.List;
import java.util.Optional;
import java.security.Principal;
import javax.validation.Valid;

import com.oliver.financial.domain.Account;
import com.oliver.financial.domain.Card;
import com.oliver.financial.domain.Movement;
import com.oliver.financial.domain.enumeration.CardType;
import com.oliver.financial.domain.enumeration.MovementType;
import com.oliver.financial.service.AccountService;
import com.oliver.financial.service.CardService;
import com.oliver.financial.service.MovementService;
import com.oliver.financial.service.dto.*;

import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class MovementController {

    private final Logger log = LoggerFactory.getLogger(MovementController.class);

    private final MovementService movementService;

    private final CardService cardService;

    private final AccountService accountService;

    public MovementController(MovementService movementService, CardService cardService, AccountService accountService) {
        this.movementService = movementService;
        this.cardService = cardService;
        this.accountService = accountService;
    }

    @GetMapping("/movements")
    public ResponseEntity<List<Movement>> allMovements(Principal principal) {
        log.debug("Request to get all movements of: {}", principal.getName());
        Optional<Card> card = cardService.findOneByNumber(Integer.parseInt(principal.getName()));
        List<Movement> movements = movementService.findAllByAccount_Id(card.get().getAccount().getId());
        return ResponseEntity.ok().body(movements);
    }

    @PostMapping("/movements")
    public ResponseEntity<Movement> createMovement(@RequestBody @Valid MovementDTO movementDTO, Principal principal) {
        log.debug("Request to create movement: {}", movementDTO);
        Optional<Card> card = cardService.findOneByNumber(Integer.parseInt(principal.getName()));
        if (movementDTO.getType().equals(MovementType.TRANSFER_OUTGONE)) {
            if (movementDTO.getIban() == null || movementDTO.getIban().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The IBAN is mandatory");
            }
            Optional<Account> account = accountService.findOneByIban(movementDTO.getIban());
            if (!account.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid iban");
            }

        }
        if (movementDTO.getType().equals(MovementType.CASH_DEPOSITED) && ((card.get().getType().equals(CardType.DEBIT)
                && movementDTO.getAmount() > card.get().getAccount().getBalance())
                || (card.get().getType().equals(CardType.CREDIT) && movementDTO
                        .getAmount() > (card.get().getBalanceAvailable() != null ? card.get().getBalanceAvailable()
                                : 0.0f)))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }
        Movement result = movementService.save(movementDTO, card.get());
        if (card.get().getType().equals(CardType.DEBIT)) {
            Account accountUpdated = accountService.updateAccount(result, result.getAccount());
            result.setAccount(accountUpdated);
        } else {
            cardService.updateCard(result, card.get());
        }
        return ResponseEntity.ok().body(result);
    }

}
