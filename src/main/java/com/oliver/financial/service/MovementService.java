package com.oliver.financial.service;

import java.util.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oliver.financial.domain.Card;
import com.oliver.financial.domain.Movement;
import com.oliver.financial.domain.enumeration.MovementType;
import com.oliver.financial.repository.CardRepository;
import com.oliver.financial.repository.MovementRepository;

import org.slf4j.*;

@Service
public class MovementService {

    private final Logger log = LoggerFactory.getLogger(MovementService.class);

    private final MovementRepository movementRepository;
    
    public MovementService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    @Transactional(readOnly = true)
    public List<Movement> findAllByAccount_Id(Long accountId) {
        log.debug("Request to get all movements by account id : {}", accountId);
        return movementRepository.findAllByAccount_Id(accountId);
    }

}
