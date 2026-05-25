package com.hospitalms.controller.dashboard;

import com.hospitalms.core.controller.BaseController;
import com.hospitalms.util.SceneUtil;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class DashboardController extends BaseController {

    @FXML
    private void handlePatients(ActionEvent event) {
        Stage stage = getStageFromEvent(event);

        SceneUtil.changeScene(
                stage,
                "/com/hospitalms/fxml/patient/patient-list-view.fxml",
                "Hospital Management System - Patients"
        );
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        Stage stage = getStageFromEvent(event);

        SceneUtil.changeScene(
                stage,
                "/com/hospitalms/fxml/auth/login-view.fxml",
                "Hospital Management System - Login"
        );
    }

    private Stage getStageFromEvent(ActionEvent event) {
        Node source = (Node) event.getSource();
        return (Stage) source.getScene().getWindow();
    }
}