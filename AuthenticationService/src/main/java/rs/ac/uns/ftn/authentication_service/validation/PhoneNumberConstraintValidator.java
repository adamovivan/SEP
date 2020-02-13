package rs.ac.uns.ftn.authentication_service.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberConstraintValidator implements ConstraintValidator<PhoneNumberValidation, String> {

    @Override
    public void initialize(PhoneNumberValidation string) {
        /* no body */
    }

    @Override
    public boolean isValid(String customField, ConstraintValidatorContext ctx) {

        if(customField == null) {
            return false;
        }
        return customField.matches("^[0-9]{9}|[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{3}$");
    }

}
