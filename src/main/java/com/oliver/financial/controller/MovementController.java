package com.oliver.financial.controller;

import java.util.List;
import java.util.Optional;
import java.security.Principal;
import javax.validation.Valid;

import com.oliver.financial.domain.Card;
import com.oliver.financial.domain.Movement;
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

    public MovementController(MovementService movementService, CardService cardService) {
        this.movementService = movementService;
        this.cardService = cardService;
    }

    @GetMapping("/movements")
    public ResponseEntity<List<Movement>> allMovements(Principal principal) {
        log.debug("Request to get all movements of: {}", principal.getName());
        Optional<Card> card = cardService.findOneByNumber(Integer.parseInt(principal.getName()));
        if (!card.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid card number");
        }
        if (!card.get().isActivated() || card.get().getPin() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The card is not available");
        }
        List<Movement> movements = movementService.findAllByAccount_Id(card.get().getAccount().getId());
        return ResponseEntity.ok().body(movements);
    }

    /* @PostMapping("/movements/cash/deposite")
    public ResponseEntity<Movement> depositeCash(@RequestBody @Valid MovementDTO movementDTO) {
        log.debug("Request to deposite cash: {}", movementDTO);
        Optional<Card> card = cardService.findOneByNumber(movementDTO.getCardNumber());
        if (!card.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid card number");
        }
        if (!card.get().isActivated() || card.get().getPin() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The card is not available");
        }
        if (!cardService.checkCardPin(movementDTO.getPin(), card.get().getPin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid pin");
        }
        card.get().setActivated(true);
        List<Movement> movements = movementService.findAllByAccount_Id(accountId);
        return ResponseEntity.ok().body(movements);
    } */

}
