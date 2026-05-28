package com.hospitalms.controller.doctor;

import com.hospitalms.config.AppFactory;
import com.hospitalms.core.controller.BaseController;
import com.hospitalms.dto.doctor.DoctorCreateRequest;
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

            changeScene(
                    event,
                    "/com/hospitalms/fxml/doctor/doctor-list-view.fxml",
                    "Hospital Management System - Doctors"
            );

        } catch (Exception e) {
            showError("Doctor Error", e.getMessage());
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        changeScene(
                event,
                "/com/hospitalms/fxml/doctor/doctor-list-view.fxml",
                "Hospital Management System - Doctors"
        );
    }
}