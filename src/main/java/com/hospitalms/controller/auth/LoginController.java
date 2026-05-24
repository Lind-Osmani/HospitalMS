package com.hospitalms.controller.auth;

import com.hospitalms.core.controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends BaseController{

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void handleLogin(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(username == null || username.trim().isEmpty()){
            showInfo("Validation Error", "Username is required.");
            return;
        }

        if(password == null || password.trim().isEmpty()){
            showInfo("Validation Error", "Password is required.");
            return;
        }

        showInfo(
                "Login Test",
                "Username and password were entered successfully."
        );
    }
}