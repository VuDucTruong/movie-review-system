package com.vdt.authservice.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordMatchesValidator.class)
public @interface PasswordMatches {
    String message() default "PASSWORDS_NOT_MATCH";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
