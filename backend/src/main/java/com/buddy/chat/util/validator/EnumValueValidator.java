package com.buddy.chat.util.validator;


import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<EnumValue, Enum<?>> {
    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(EnumValue annotation) {
        this.enumClass = annotation.enumClass();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return Arrays.stream(enumClass.getEnumConstants())
                     .anyMatch(enumConstant -> enumConstant.equals(value));
    }
}	