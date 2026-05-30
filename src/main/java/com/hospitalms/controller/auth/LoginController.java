package com.hospitalms.controller.auth;

import com.hospitalms.core.controller.BaseController;
import com.hospitalms.util.SceneUtil;
import com.hospitalms.validator.LoginValidator;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.hospitalms.core.navigation.PageTitles;
import com.hospitalms.core.navigation.ViewPaths;

public class LoginController extends BaseController{

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final LoginValidator loginValidator = new LoginValidator();

    @FXML
    public void handleLogin(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        String validationError = loginValidator.validate(username, password);

        if(validationError != null){
            showError("Validation Error", validationError);
            return;
        }

        Stage stage = (Stage) usernameField.getScene().getWindow();

        SceneUtil.changeScene(
                stage,
                ViewPaths.DASHBOARD,
                PageTitles.DASHBOARD
        );

    }
}