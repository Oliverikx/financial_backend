package com.oliver.financial.domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.oliver.financial.domain.enumeration.MovementType;
import com.oliver.financial.domain.enumeration.MovementTypeConverter;

@Entity
@Table(name = "movement")
public class Movement implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Float amount;

    @NotNull
    @Convert(converter = MovementTypeConverter.class)
    @Column(name = "type", nullable = false)
    private MovementType type;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "account_id")
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Movement [amount=" + amount + ", account=" + account + ", id=" + id + ", type=" + type + "]";
    }

    
}
