package com.oliver.financial.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangePinCardDTO {
    @NotNull
    private Integer cardNumber;

    private String oldPin;

    @NotNull
    @NotBlank(message = "New pin is mandatory")
    private String newPin;

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getOldPin() {
        return oldPin;
    }

    public void setOldPin(String oldPin) {
        this.oldPin = oldPin;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }

    @Override
    public String toString() {
        return "ChangePinCardDTO [cardNumber=" + cardNumber + ", newPin=" + newPin + ", oldPin=" + oldPin + "]";
    }

}
