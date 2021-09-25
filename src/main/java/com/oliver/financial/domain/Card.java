package com.oliver.financial.domain;

import java.io.Serializable;
import com.oliver.financial.domain.enumeration.CardType;
import com.oliver.financial.domain.enumeration.CardTypeConverter;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "card")
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false, unique = true)
    private Integer number;

    @NotNull
    @Convert(converter = CardTypeConverter.class)
    @Column(name = "type", nullable = false)
    private CardType type;

    @Column(name = "pin")
    private String pin;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "balance_available")
    private Float balanceAvailable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Float getBalanceAvailable() {
        return balanceAvailable;
    }

    public void setBalanceAvailable(Float balanceAvailable) {
        this.balanceAvailable = balanceAvailable;
    }

    @Override
    public String toString() {
        return "Card [activated=" + activated + ", balanceAvailable=" + balanceAvailable + ", account=" + account + ", id="
                + id + ", number=" + number + ", pin=" + pin + ", type=" + type + "]";
    }

    
}
