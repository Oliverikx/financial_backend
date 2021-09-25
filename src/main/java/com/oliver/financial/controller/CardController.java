package com.oliver.financial.controller;

import java.util.Optional;

import javax.validation.Valid;

import com.oliver.financial.domain.Card;
import com.oliver.financial.service.CardService;
import com.oliver.financial.service.dto.*;

import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class CardController {

    private final Logger log = LoggerFactory.getLogger(CardController.class);

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/card/activate")
    public ResponseEntity<Card> registerAccount(@RequestBody @Valid CardNumberDTO cardNumberDTO) {
        log.debug("Request to activate card: {}", cardNumberDTO);
        Optional<Card> card = cardService.findOneByNumber(cardNumberDTO.getCardNumber());
        if (!card.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid card number");
        }
        if (card.get().isActivated()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The card is already activated");
        }
        card.get().setActivated(true);
        Card result = cardService.save(card.get());
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/card/pin")
    public ResponseEntity<Card> changePin(@RequestBody @Valid ChangePinCardDTO changePinCardDTO) {
        log.debug("Request to change pin card: {}", changePinCardDTO);
        Optional<Card> card = cardService.findOneByNumber(changePinCardDTO.getCardNumber());
        if (!card.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid card number");
        }
        if (card.get().isActivated() && card.get().getPin() != null
                && (changePinCardDTO.getOldPin() == null || changePinCardDTO.getOldPin().isEmpty()
                        || !cardService.checkCardPin(changePinCardDTO.getOldPin(), card.get().getPin()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid old pin");
        }
        Card result = cardService.changePin(card.get(), changePinCardDTO.getNewPin());
        return ResponseEntity.ok().body(result);
    }
}
