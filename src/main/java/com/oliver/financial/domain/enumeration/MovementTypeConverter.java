package com.oliver.financial.domain.enumeration;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MovementTypeConverter implements AttributeConverter<MovementType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MovementType attribute) {
        if (attribute == null)
            return null;

        switch (attribute) {
            case TRANSFER_INCOME:
                return 1;
            case TRANSFER_OUTGONE:
                return 2;
            case CASH_WITHDRAWN:
                return 3;
            case CASH_DEPOSITED:
                return 4;
            default:
                throw new IllegalArgumentException(attribute + " not supported.");
        }
    }

    @Override
    public MovementType convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;

        switch (dbData) {
            case 1:
                return MovementType.TRANSFER_INCOME;
            case 2:
                return MovementType.TRANSFER_OUTGONE;
            case 3:
                return MovementType.CASH_WITHDRAWN;
            case 4:
                return MovementType.CASH_DEPOSITED;
            default:
                throw new IllegalArgumentException(dbData + " not supported.");
        }
    }
}
