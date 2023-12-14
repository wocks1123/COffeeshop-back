package com.wocks.coffeeshopback.common.validator;

import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wocks.coffeeshopback.common.annotation.EnumValid;


public class EnumValidator implements ConstraintValidator<EnumValid, String> {
    private EnumValid annotation;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (ObjectUtils.isEmpty(value)) return false;

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value.equals(enumValue.toString())
                    || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))) {
                    return true;
                }
            }
        }
        return false;
    }
}