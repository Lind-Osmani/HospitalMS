package com.hospitalms.controller.doctor;

import com.hospitalms.core.controller.BaseController;
import com.hospitalms.validator.DoctorValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DoctorFormController extends BaseController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField specializationField;

    @FXML
    private ComboBox<String> departmentComboBox;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextArea addressArea;

    private final DoctorValidator doctorValidator = new DoctorValidator();

    @FXML
    private void initialize() {
        departmentComboBox.getItems().addAll(
                "Emergency",
                "Cardiology",
                "Neurology",
                "Pediatrics",
                "Orthopedics",
                "General Medicine"
        );
    }

    @FXML
    private void handleSaveDoctor() {
        String validationError = doctorValidator.validate(
                firstNameField.getText(),
                lastNameField.getText(),
                specializationField.getText(),
                phoneField.getText(),
                emailField.getText()
        );

        if (validationError != null) {
            showError("Validation Error", validationError);
            return;
        }

        showInfo(
                "Success",
                "Doctor form is valid. Database saving will be added later."
        );

        clearForm();
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        changeScene(
                event,
                "/com/hospitalms/fxml/doctor/doctor-list-view.fxml",
                "Hospital Management System - Doctors"
        );
    }

    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        specializationField.clear();
        departmentComboBox.setValue(null);
        phoneField.clear();
        emailField.clear();
        addressArea.clear();
    }
}