package com.hospitalms.controller.patient;

import com.hospitalms.config.AppFactory;
import com.hospitalms.core.controller.BaseController;
import com.hospitalms.dto.patient.PatientResponse;
import com.hospitalms.mapper.PatientMapper;
import com.hospitalms.model.Patient;
import com.hospitalms.service.PatientService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class PatientListController extends BaseController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<PatientResponse> patientTable;

    @FXML
    private TableColumn<PatientResponse, Long> idColumn;

    @FXML
    private TableColumn<PatientResponse, String> firstNameColumn;

    @FXML
    private TableColumn<PatientResponse, String> lastNameColumn;

    @FXML
    private TableColumn<PatientResponse, String> phoneColumn;

    @FXML
    private TableColumn<PatientResponse, String> emailColumn;

    private final PatientService patientService = AppFactory.getPatientService();
    private final PatientMapper patientMapper = new PatientMapper();

    @FXML
    private void initialize() {
        setupTableColumns();
        loadPatients();
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();

        List<Patient> patients = patientService.searchPatients(keyword);
        showPatientsInTable(patients);
    }

    @FXML
    private void handleRefresh() {
        searchField.clear();
        loadPatients();
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

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadPatients() {
        List<Patient> patients = patientService.getAllPatients();
        showPatientsInTable(patients);
    }

    private void showPatientsInTable(List<Patient> patients) {
        List<PatientResponse> patientResponses = patientMapper.toResponseList(patients);

        ObservableList<PatientResponse> observablePatients =
                FXCollections.observableArrayList(patientResponses);

        patientTable.setItems(observablePatients);
    }
}