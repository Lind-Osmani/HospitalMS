package com.hospitalms.validator;

import com.hospitalms.util.ValidationUtil;
import com.hospitalms.core.validator.BasePersonValidator;

public class DoctorValidator extends BasePersonValidator{
    public String validate(String firstName, String lastName, String specialization, String phone, String email){
        String firstNameError = validateFirstName(firstName);
        if (firstNameError != null) {
            return firstNameError;
        }

        String lastNameError = validateLastName(lastName);
        if (lastNameError != null) {
            return lastNameError;
        }

        if (ValidationUtil.isBlank(specialization)) {
            return "Specialization is required.";
        }

        String phoneError = validatePhone(phone);
        if (phoneError != null) {
            return phoneError;
        }

        String emailError = validateEmail(email);
        if (emailError != null) {
            return emailError;
        }

        return null;
    }
}