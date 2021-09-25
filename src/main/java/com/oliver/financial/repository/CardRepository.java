package com.oliver.financial.repository;

import java.util.Optional;

import com.oliver.financial.domain.Card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findOneByNumber(Integer number);

}
