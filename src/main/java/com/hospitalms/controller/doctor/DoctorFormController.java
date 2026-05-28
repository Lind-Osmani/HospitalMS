package com.hospitalms.controller.doctor;

import com.hospitalms.config.AppFactory;
import com.hospitalms.config.DoctorFormContext;
import com.hospitalms.core.controller.BaseController;
import com.hospitalms.dto.doctor.DoctorCreateRequest;
import com.hospitalms.dto.doctor.DoctorUpdateRequest;
import com.hospitalms.model.Doctor;
import com.hospitalms.service.DoctorService;
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
    private final DoctorService doctorService = AppFactory.getDoctorService();

    @FXML
    private void initialize() {
        setupDepartmentComboBox();

        if (DoctorFormContext.isEditMode()) {
            loadDoctorForEditing();
        }
    }

    @FXML
    private void handleSaveDoctor(ActionEvent event) {
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

        if (DoctorFormContext.isEditMode()) {
            updateDoctor(event);
        } else {
            createDoctor(event);
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        DoctorFormContext.clear();
        goBackToDoctorList(event);
    }

    private void setupDepartmentComboBox() {
        departmentComboBox.getItems().addAll(
                "Emergency",
                "Cardiology",
                "Neurology",
                "Pediatrics",
                "Orthopedics",
                "General Medicine"
        );
    }

    private void loadDoctorForEditing() {
        Long doctorId = DoctorFormContext.getEditingDoctorId();

        Doctor doctor = doctorService.getDoctorById(doctorId);

        firstNameField.setText(doctor.getFirstName());
        lastNameField.setText(doctor.getLastName());
        specializationField.setText(doctor.getSpecialization());
        departmentComboBox.setValue(doctor.getDepartment());
        phoneField.setText(doctor.getPhone());
        emailField.setText(doctor.getEmail());
        addressArea.setText(doctor.getAddress());
    }

    private void createDoctor(ActionEvent event) {
        DoctorCreateRequest request = new DoctorCreateRequest(
                firstNameField.getText(),
                lastNameField.getText(),
                specializationField.getText(),
                departmentComboBox.getValue(),
                phoneField.getText(),
                emailField.getText(),
                addressArea.getText()
        );

        try {
            Doctor savedDoctor = doctorService.createDoctor(request);

            showInfo(
                    "Success",
                    "Doctor created successfully: "
                            + savedDoctor.getFirstName()
                            + " "
                            + savedDoctor.getLastName()
            );

            goBackToDoctorList(event);

        } catch (Exception e) {
            showError("Doctor Error", e.getMessage());
        }
    }

    private void updateDoctor(ActionEvent event) {
        Long doctorId = DoctorFormContext.getEditingDoctorId();

        DoctorUpdateRequest request = new DoctorUpdateRequest(
                firstNameField.getText(),
                lastNameField.getText(),
                specializationField.getText(),
                departmentComboBox.getValue(),
                phoneField.getText(),
                emailField.getText(),
                addressArea.getText()
        );

        try {
            Doctor updatedDoctor = doctorService.updateDoctor(doctorId, request);

            showInfo(
                    "Success",
                    "Doctor updated successfully: "
                            + updatedDoctor.getFirstName()
                            + " "
                            + updatedDoctor.getLastName()
            );

            DoctorFormContext.clear();
            goBackToDoctorList(event);

        } catch (Exception e) {
            showError("Doctor Error", e.getMessage());
        }
    }

    private void goBackToDoctorList(ActionEvent event) {
        changeScene(
                event,
                "/com/hospitalms/fxml/doctor/doctor-list-view.fxml",
                "Hospital Management System - Doctors"
        );
    }
}