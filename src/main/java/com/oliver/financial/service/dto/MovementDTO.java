package com.oliver.financial.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.oliver.financial.domain.enumeration.MovementType;

public class MovementDTO {
    @NotNull
    private Float amount;

    private String iban;

    @NotNull
    private MovementType type;

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MovementDTO [amount=" + amount + ", iban=" + iban + ", type=" + type + "]";
    }
    
    
    
}
