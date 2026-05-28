package com.hospitalms.controller.doctor;

import com.hospitalms.config.AppFactory;
import com.hospitalms.core.controller.BaseController;
import com.hospitalms.dto.doctor.DoctorResponse;
import com.hospitalms.mapper.DoctorMapper;
import com.hospitalms.model.Doctor;
import com.hospitalms.service.DoctorService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class DoctorListController extends BaseController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<DoctorResponse> doctorTable;

    @FXML
    private TableColumn<DoctorResponse, Long> idColumn;

    @FXML
    private TableColumn<DoctorResponse, String> firstNameColumn;

    @FXML
    private TableColumn<DoctorResponse, String> lastNameColumn;

    @FXML
    private TableColumn<DoctorResponse, String> specializationColumn;

    @FXML
    private TableColumn<DoctorResponse, String> phoneColumn;

    @FXML
    private TableColumn<DoctorResponse, String> emailColumn;

    private final DoctorService doctorService = AppFactory.getDoctorService();
    private final DoctorMapper doctorMapper = new DoctorMapper();

    @FXML
    private void initialize() {
        setupTableColumns();
        loadDoctors();
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();

        List<Doctor> doctors = doctorService.searchDoctors(keyword);
        showDoctorsInTable(doctors);
    }

    @FXML
    private void handleRefresh() {
        searchField.clear();
        loadDoctors();
    }

    @FXML
    private void handleAddDoctor(ActionEvent event) {
        changeScene(
                event,
                "/com/hospitalms/fxml/doctor/doctor-form-view.fxml",
                "Hospital Management System - Add Doctor"
        );
    }

    @FXML
    private void handleEditDoctor() {
        showInfo("Edit Doctor", "Edit Doctor functionality will be added later.");
    }

    @FXML
    private void handleDeleteDoctor() {
        showInfo("Delete Doctor", "Delete Doctor functionality will be added later.");
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
        specializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        showDoctorsInTable(doctors);
    }

    private void showDoctorsInTable(List<Doctor> doctors) {
        List<DoctorResponse> doctorResponses = doctorMapper.toResponseList(doctors);

        ObservableList<DoctorResponse> observableDoctors =
                FXCollections.observableArrayList(doctorResponses);

        doctorTable.setItems(observableDoctors);
    }
}