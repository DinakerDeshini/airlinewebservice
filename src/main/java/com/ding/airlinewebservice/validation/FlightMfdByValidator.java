package com.ding.airlinewebservice.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class FlightMfdByValidator implements ConstraintValidator<FlightMfdBy, String>{

    List<String> approvedManufacturers = Arrays.asList("Boeing","Concord","AirBus");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if (value==null || value.isEmpty()) {
            return false;
        }

        if (approvedManufacturers.contains(value)) {
            return true;
        } else {
            return false;
        }

    }
}
