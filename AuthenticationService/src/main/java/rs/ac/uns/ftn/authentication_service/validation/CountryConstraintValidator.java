package rs.ac.uns.ftn.authentication_service.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CountryConstraintValidator implements ConstraintValidator<CountryValidation, String> {

    @Override
    public void initialize(CountryValidation string) {
        /* no body */
    }

    @Override
    public boolean isValid(String customField, ConstraintValidatorContext ctx) {

        if(customField == null) {
            return false;
        }
        return customField.matches("^[A-Z]{2}$");
    }
}
