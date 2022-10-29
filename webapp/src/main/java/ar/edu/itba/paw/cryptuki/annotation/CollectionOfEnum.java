package ar.edu.itba.paw.cryptuki.annotation;

import ar.edu.itba.paw.cryptuki.validator.CollectionOfEnumValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CollectionOfEnumValidator.class)
public @interface CollectionOfEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "all elements in collection must be any of {enumClass}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}