package com.hospitalms.controller.patient;

import com.hospitalms.core.controller.BaseController;
import com.hospitalms.dto.patient.PatientCreateRequest;
import com.hospitalms.model.Patient;
import com.hospitalms.service.PatientService;
import com.hospitalms.service.impl.PatientServiceImpl;
import com.hospitalms.validator.PatientValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PatientFormController extends BaseController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private ComboBox<String> bloodGroupComboBox;

    @FXML
    private TextArea addressArea;

    private final PatientValidator patientValidator = new PatientValidator();
    private final PatientService patientService = new PatientServiceImpl();

    @FXML
    private void initialize() {
        genderComboBox.getItems().addAll("Male", "Female", "Other");

        bloodGroupComboBox.getItems().addAll(
                "A+", "A-",
                "B+", "B-",
                "AB+", "AB-",
                "O+", "O-"
        );
    }

    @FXML
    private void handleSavePatient() {
        String validationError = patientValidator.validate(
                firstNameField.getText(),
                lastNameField.getText(),
                phoneField.getText(),
                emailField.getText()
        );

        if (validationError != null) {
            showError("Validation Error", validationError);
            return;
        }

        PatientCreateRequest request = new PatientCreateRequest(
                firstNameField.getText(),
                lastNameField.getText(),
                genderComboBox.getValue(),
                dateOfBirthPicker.getValue(),
                phoneField.getText(),
                emailField.getText(),
                bloodGroupComboBox.getValue(),
                addressArea.getText()
        );

        Patient savedPatient = patientService.createPatient(request);

        showInfo(
                "Success",
                "Patient created successfully: "
                        + savedPatient.getFirstName()
                        + " "
                        + savedPatient.getLastName()
        );

        clearForm();
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        changeScene(
                event,
                "/com/hospitalms/fxml/patient/patient-list-view.fxml",
                "Hospital Management System - Patients"
        );
    }

    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        genderComboBox.setValue(null);
        dateOfBirthPicker.setValue(null);
        phoneField.clear();
        emailField.clear();
        bloodGroupComboBox.setValue(null);
        addressArea.clear();
    }
}