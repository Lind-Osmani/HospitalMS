package com.hospitalms.controller.patient;

import com.hospitalms.config.AppFactory;
import com.hospitalms.config.PatientFormContext;
import com.hospitalms.core.controller.BaseController;
import com.hospitalms.dto.patient.PatientCreateRequest;
import com.hospitalms.dto.patient.PatientUpdateRequest;
import com.hospitalms.model.Patient;
import com.hospitalms.service.PatientService;
import com.hospitalms.validator.PatientValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import com.hospitalms.core.navigation.PageTitles;
import com.hospitalms.core.navigation.ViewPaths;

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
    private final PatientService patientService = AppFactory.getPatientService();

    @FXML
    private void initialize() {
        setupComboBoxes();

        if (PatientFormContext.isEditMode()) {
            loadPatientForEditing();
        }
    }

    @FXML
    private void handleSavePatient(ActionEvent event) {
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

        if (PatientFormContext.isEditMode()) {
            updatePatient(event);
        } else {
            createPatient(event);
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        PatientFormContext.clear();

        changeScene(
                event,
                ViewPaths.PATIENT_LIST,
                PageTitles.PATIENTS
        );
    }

    private void setupComboBoxes() {
        genderComboBox.getItems().addAll("Male", "Female", "Other");

        bloodGroupComboBox.getItems().addAll(
                "A+", "A-",
                "B+", "B-",
                "AB+", "AB-",
                "O+", "O-"
        );
    }

    private void loadPatientForEditing() {
        Long patientId = PatientFormContext.getEditingPatientId();

        Patient patient = patientService.getPatientById(patientId);

        firstNameField.setText(patient.getFirstName());
        lastNameField.setText(patient.getLastName());
        genderComboBox.setValue(patient.getGender());
        dateOfBirthPicker.setValue(patient.getDateOfBirth());
        phoneField.setText(patient.getPhone());
        emailField.setText(patient.getEmail());
        bloodGroupComboBox.setValue(patient.getBloodGroup());
        addressArea.setText(patient.getAddress());
    }

    private void createPatient(ActionEvent event) {
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

        try {
            Patient savedPatient = patientService.createPatient(request);

            showInfo(
                    "Success",
                    "Patient created successfully: "
                            + savedPatient.getFirstName()
                            + " "
                            + savedPatient.getLastName()
            );

            goBackToPatientList(event);

        } catch (Exception e) {
            showError("Patient Error", e.getMessage());
        }
    }

    private void updatePatient(ActionEvent event) {
        Long patientId = PatientFormContext.getEditingPatientId();

        PatientUpdateRequest request = new PatientUpdateRequest(
                firstNameField.getText(),
                lastNameField.getText(),
                genderComboBox.getValue(),
                dateOfBirthPicker.getValue(),
                phoneField.getText(),
                emailField.getText(),
                bloodGroupComboBox.getValue(),
                addressArea.getText()
        );

        try {
            Patient updatedPatient = patientService.updatePatient(patientId, request);

            showInfo(
                    "Success",
                    "Patient updated successfully: "
                            + updatedPatient.getFirstName()
                            + " "
                            + updatedPatient.getLastName()
            );

            PatientFormContext.clear();
            goBackToPatientList(event);

        } catch (Exception e) {
            showError("Patient Error", e.getMessage());
        }
    }

    private void goBackToPatientList(ActionEvent event) {
        changeScene(
                event,
                ViewPaths.PATIENT_LIST,
                PageTitles.PATIENTS
        );
    }
}