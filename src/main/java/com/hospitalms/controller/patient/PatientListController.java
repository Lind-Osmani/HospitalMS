package com.hospitalms.controller.patient;

import com.hospitalms.core.controller.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import com.hospitalms.config.AppFactory;
import com.hospitalms.model.Patient;
import com.hospitalms.service.PatientService;

public class PatientListController extends BaseController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<?> patientTable;

    private final PatientService patientService = AppFactory.getPatientService();

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
    private void handleAddPatient(ActionEvent event) {
        changeScene(
                event,
                "/com/hospitalms/fxml/patient/patient-form-view.fxml",
                "Hospital Management System - Add Patient"
        );
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        changeScene(
                event,
                "/com/hospitalms/fxml/dashboard/dashboard-view.fxml",
                "Hospital Management System - Dashboard"
        );
    }


}