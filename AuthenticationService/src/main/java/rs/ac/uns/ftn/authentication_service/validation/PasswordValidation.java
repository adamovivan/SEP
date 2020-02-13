package rs.ac.uns.ftn.authentication_service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidation {

    String message() default "Password is not in a valid format!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
