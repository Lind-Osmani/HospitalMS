package com.hospitalms.validator;

import com.hospitalms.util.ValidationUtil;

public class PatientValidator {

    public String validate(String firstName, String lastName, String phone, String email) {
        if (ValidationUtil.isBlank(firstName)) {
            return "First name is required.";
        }

        if (ValidationUtil.isBlank(lastName)) {
            return "Last name is required.";
        }

        if (ValidationUtil.isBlank(phone)) {
            return "Phone number is required.";
        }

        if (!ValidationUtil.isBlank(email) && !ValidationUtil.isValidEmail(email)) {
            return "Email must be valid.";
        }

        return null;
    }
}