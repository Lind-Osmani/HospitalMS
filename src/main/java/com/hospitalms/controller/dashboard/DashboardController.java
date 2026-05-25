package com.hospitalms.controller.dashboard;

import com.hospitalms.core.controller.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class DashboardController extends BaseController {

    @FXML
    private void handlePatients(ActionEvent event) {
        changeScene(
                event,
                "/com/hospitalms/fxml/patient/patient-list-view.fxml",
                "Hospital Management System - Patients"
        );
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        changeScene(
                event,
                "/com/hospitalms/fxml/auth/login-view.fxml",
                "Hospital Management System - Login"
        );
    }
}