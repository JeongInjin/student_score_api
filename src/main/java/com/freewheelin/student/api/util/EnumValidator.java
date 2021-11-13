package com.freewheelin.student.api.util;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumPattern, String> {
    @Override
    public void initialize(EnumPattern constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        boolean result = false;
        String[] enumStr = {"ELEMENTARY", "MIDDLE", "HIGH"};
        for(String v : enumStr){
            if(v.equalsIgnoreCase(value.toString())){
                result = true;
                break;
            }
        }

        return result;
    }
}
