package rs.ac.uns.ftn.authentication_service.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<PasswordValidation, String> {

    @Override
    public void initialize(PasswordValidation string) {
        /* no body */
    }

    @Override
    public boolean isValid(String customField, ConstraintValidatorContext ctx) {

        if(customField == null) {
            return false;
        }
        return customField.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,}$");
    }

}
