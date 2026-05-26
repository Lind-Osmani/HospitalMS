package com.hospitalms.controller.doctor;

import com.hospitalms.core.controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;


public class DoctorListController extends BaseController{

    @FXML
    private TextField searchField;

    @FXML
    private TableView<?> doctorTable;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableColumn<?, ?> firstNameColumn;

    @FXML
    private TableColumn<?, ?> lastNameColumn;

    @FXML
    private TableColumn<?, ?> specializationColumn;

    @FXML
    private TableColumn<?, ?> phoneColumn;

    @FXML
    private TableColumn<?, ?> emailColumn;

    @FXML
    private void initialize(){
        showInfo("Doctor Module", "Doctor Management screen loaded successfully.");
    }

    @FXML
    private void handleSearch(){
        String keyword = searchField.getText();

        if(keyword == null || keyword.trim().isEmpty()){
            showError("Search Error", "Please enter a doctor name or specialization.");
            return;
        }

        showInfo("Search", "Searching for doctor: " + keyword);
    }

    @FXML
    private void handleRefresh(){
        searchField.clear();
        showInfo("Refresh", "Doctor table refresh will be added later.");
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
    private void handleEditDoctor(){
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
}