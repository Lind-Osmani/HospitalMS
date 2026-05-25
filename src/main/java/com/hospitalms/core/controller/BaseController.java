package com.hospitalms.core.controller;

import com.hospitalms.util.SceneUtil;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public abstract class BaseController {

    protected void showInfo(String title, String message) {
        showAlert(Alert.AlertType.INFORMATION, title, message);
    }

    protected void showError(String title, String message) {
        showAlert(Alert.AlertType.ERROR, title, message);
    }

    protected void showWarning(String title, String message) {
        showAlert(Alert.AlertType.WARNING, title, message);
    }

    protected void changeScene(ActionEvent event, String fxmlPath, String title) {
        Stage stage = getStageFromEvent(event);
        SceneUtil.changeScene(stage, fxmlPath, title);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Stage getStageFromEvent(ActionEvent event) {
        Node source = (Node) event.getSource();
        return (Stage) source.getScene().getWindow();
    }
}