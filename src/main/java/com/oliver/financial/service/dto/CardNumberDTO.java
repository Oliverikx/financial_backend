package com.oliver.financial.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CardNumberDTO {

    @NotNull
    private Integer cardNumber;

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "CardNumberDTO [cardNumber=" + cardNumber + "]";
    }


    
}
