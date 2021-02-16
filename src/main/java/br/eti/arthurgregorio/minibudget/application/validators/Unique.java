package br.eti.arthurgregorio.minibudget.application.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
public @interface Unique {

    String attribute();

    Class<?> domainClass();

    String message() default "Value for field {0} is not unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
