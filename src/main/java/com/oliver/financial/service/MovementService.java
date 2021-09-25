package com.oliver.financial.service;

import java.util.List;

import com.oliver.financial.domain.Card;
import com.oliver.financial.domain.Movement;
import com.oliver.financial.repository.MovementRepository;
import com.oliver.financial.service.dto.MovementDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovementService {

    private final Logger log = LoggerFactory.getLogger(MovementService.class);

    private final MovementRepository movementRepository;
    
    public MovementService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    public Movement save(MovementDTO movementDTO, Card card){
        Movement movement = new Movement();
        movement.setAmount(movementDTO.getAmount());
        movement.setType(movementDTO.getType());
        movement.setAccount(card.getAccount());
        return movementRepository.save(movement);
    }

    @Transactional(readOnly = true)
    public List<Movement> findAllByAccount_Id(Long accountId) {
        log.debug("Request to get all movements by account id : {}", accountId);
        return movementRepository.findAllByAccount_Id(accountId);
    }

}
