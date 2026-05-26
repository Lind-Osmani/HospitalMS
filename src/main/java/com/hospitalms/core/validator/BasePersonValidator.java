package com.hospitalms.core.validator;

import com.hospitalms.util.ValidationUtil;

public abstract class BasePersonValidator{
    protected String validateFirstName(String firstName) {
        if (ValidationUtil.isBlank(firstName)) {
            return "First name is required.";
        }

        if (!ValidationUtil.hasMinimumLength(firstName, 2)) {
            return "First name must have at least 2 characters.";
        }

        return null;
    }

    protected String validateLastName(String lastName) {
        if (ValidationUtil.isBlank(lastName)) {
            return "Last name is required.";
        }

        if (!ValidationUtil.hasMinimumLength(lastName, 2)) {
            return "Last name must have at least 2 characters.";
        }

        return null;
    }

    protected String validatePhone(String phone) {
        if (ValidationUtil.isBlank(phone)) {
            return "Phone number is required.";
        }

        if (!ValidationUtil.isValidPhone(phone)) {
            return "Phone number is not valid.";
        }

        return null;
    }

    protected String validateEmail(String email) {
        if (!ValidationUtil.isValidEmail(email)) {
            return "Email must be valid.";
        }

        return null;
    }
}
