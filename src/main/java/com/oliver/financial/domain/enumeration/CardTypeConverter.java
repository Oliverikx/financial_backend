package com.oliver.financial.domain.enumeration;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CardTypeConverter implements AttributeConverter<CardType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CardType attribute) {
        if (attribute == null)
            return null;

        switch (attribute) {
            case CREDIT:
                return 1;
            case DEBIT:
                return 2;
            default:
                throw new IllegalArgumentException(attribute + " not supported.");
        }
    }

    @Override
    public CardType convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;

        switch (dbData) {
            case 1:
                return CardType.CREDIT;
            case 2:
                return CardType.DEBIT;
            default:
                throw new IllegalArgumentException(dbData + " not supported.");
        }
    }
}