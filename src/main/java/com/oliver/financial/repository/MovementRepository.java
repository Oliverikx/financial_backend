package com.oliver.financial.repository;

import java.util.List;

import com.oliver.financial.domain.Movement;
import com.oliver.financial.domain.enumeration.MovementType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findAllByAccount_Id(Long accountId);

}