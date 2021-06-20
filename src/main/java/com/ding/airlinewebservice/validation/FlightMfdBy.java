package com.ding.airlinewebservice.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = FlightMfdByValidator.class)
public @interface FlightMfdBy {

    String message() default "Provided manufacturer is not in the approved list";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
