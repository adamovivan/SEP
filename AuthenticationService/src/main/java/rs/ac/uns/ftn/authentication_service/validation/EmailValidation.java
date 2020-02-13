package rs.ac.uns.ftn.authentication_service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValidation {

    String message() default "Email is not in a valid format!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
