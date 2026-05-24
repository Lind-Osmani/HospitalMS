package com.hospitalms.controller.auth;

import com.hospitalms.core.controller.BaseController;

public class LoginController extends BaseController{

    public void handleLogin(){

        showInfo(
                "Login Test",
                "Login button works successfully!"
        );
    }
}