package com.hospitalms.validator;

import com.hospitalms.util.ValidationUtil;

public class LoginValidator {

    public String validate(String username, String password) {
        if (ValidationUtil.isBlank(username)) {
            return "Username is required.";
        }

        if (ValidationUtil.isBlank(password)) {
            return "Password is required.";
        }

        return null;
    }
}