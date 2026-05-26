package com.hospitalms.validator;

import com.hospitalms.util.ValidationUtil;

public class DoctorValidator{
    public String validate(String firstName, String lastName, String specialization, String phone, String email){
        if(ValidationUtil.isBlank(firstName)){
            return "First name is required.";
        }

        if(!ValidationUtil.hasMinimumLength(firstName, 2)){
            return "First name must have at least 2 characters.";
        }

        if(ValidationUtil.isBlank(lastName)){
            return "Last name is required.";
        }

        if(!ValidationUtil.hasMinimumLength(lastName, 2)){
            return "Last name must have at least 2 characters.";
        }

        if(ValidationUtil.isBlank(specialization)){
            return "Specialization is required.";
        }

        if(ValidationUtil.isBlank(phone)){
            return "Phone is required.";
        }

        if(ValidationUtil.isBlank(email)){
            return "Email is required.";
        }

        if (!ValidationUtil.isValidPhone(phone)) {
            return "Phone number is not valid.";
        }

        if (!ValidationUtil.isValidEmail(email)) {
            return "Email must be valid.";
        }

        return null;
    }
}