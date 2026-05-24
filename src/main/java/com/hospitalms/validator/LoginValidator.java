package com.hospitalms.validator;

public class LoginValidator {

    public String validate(String username, String password){

        if(username == null || username.trim().isEmpty()){
            return "Username is required.";
        }

        if(password == null || password.trim().isEmpty()){
            return "Password is required.";
        }

        return null;
    }

}
