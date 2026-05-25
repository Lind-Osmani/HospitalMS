package com.hospitalms.controller.patient;

import com.hospitalms.core.controller.BaseController;
import com.hospitalms.util.SceneUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientListController extends BaseController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<?> patientTable;

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();

        if (keyword == null || keyword.trim().isEmpty()) {
            showError("Search Error", "Please enter a search keyword.");
            return;
        }

        showInfo("Search", "Searching for: " + keyword);
    }

    @FXML
    private void handleAddPatient() {
        showInfo("Add Patient", "Add Patient screen will be added next.");
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        Stage stage = getStageFromEvent(event);

        SceneUtil.changeScene(
                stage,
                "/com/hospitalms/fxml/dashboard/dashboard-view.fxml",
                "Hospital Management System - Dashboard"
        );
    }

    private Stage getStageFromEvent(ActionEvent event) {
        Node source = (Node) event.getSource();
        return (Stage) source.getScene().getWindow();
    }
}