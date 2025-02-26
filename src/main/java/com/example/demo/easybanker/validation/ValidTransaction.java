package com.example.demo.easybanker.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TransactionValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTransaction {
    String message() default "Receiver is required for TRANSFER transactions";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

