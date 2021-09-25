package com.oliver.financial.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class CardDTO {
    @NotNull
    private Integer cardNumber;

    @NotNull
    @NotBlank(message = "Pin is mandatory")
    private String pin;

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "CardDTO [cardNumber=" + cardNumber + ", pin=" + pin + "]";
    }

    
}
