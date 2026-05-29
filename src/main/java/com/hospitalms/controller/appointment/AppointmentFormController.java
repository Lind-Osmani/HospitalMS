package com.hospitalms.controller.appointment;

import com.hospitalms.config.AppFactory;
import com.hospitalms.core.controller.BaseController;
import com.hospitalms.core.ui.ComboBoxItem;
import com.hospitalms.dto.appointment.AppointmentCreateRequest;
import com.hospitalms.model.Appointment;
import com.hospitalms.model.AppointmentStatus;
import com.hospitalms.model.Doctor;
import com.hospitalms.model.Patient;
import com.hospitalms.service.AppointmentService;
import com.hospitalms.service.DoctorService;
import com.hospitalms.service.PatientService;
import com.hospitalms.validator.AppointmentValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.time.LocalTime;

public class AppointmentFormController extends BaseController {

    @FXML
    private ComboBox<ComboBoxItem> patientComboBox;

    @FXML
    private ComboBox<ComboBoxItem> doctorComboBox;

    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private ComboBox<String> appointmentTimeComboBox;

    @FXML
    private ComboBox<AppointmentStatus> statusComboBox;

    @FXML
    private TextArea reasonArea;

    private final PatientService patientService = AppFactory.getPatientService();
    private final DoctorService doctorService = AppFactory.getDoctorService();
    private final AppointmentService appointmentService = AppFactory.getAppointmentService();
    private final AppointmentValidator appointmentValidator = new AppointmentValidator();

    @FXML
    private void initialize() {
        loadPatients();
        loadDoctors();
        loadTimes();
        loadStatuses();
    }

    @FXML
    private void handleSaveAppointment(ActionEvent event) {
        ComboBoxItem selectedPatient = patientComboBox.getValue();
        ComboBoxItem selectedDoctor = doctorComboBox.getValue();

        Long patientId = selectedPatient != null ? selectedPatient.getId() : null;
        Long doctorId = selectedDoctor != null ? selectedDoctor.getId() : null;

        LocalTime appointmentTime = appointmentTimeComboBox.getValue() != null
                ? LocalTime.parse(appointmentTimeComboBox.getValue())
                : null;

        AppointmentStatus status = statusComboBox.getValue();

        String validationError = appointmentValidator.validate(
                patientId,
                doctorId,
                appointmentDatePicker.getValue(),
                appointmentTime,
                reasonArea.getText(),
                status
        );

        if (validationError != null) {
            showError("Validation Error", validationError);
            return;
        }

        AppointmentCreateRequest request = new AppointmentCreateRequest(
                patientId,
                doctorId,
                appointmentDatePicker.getValue(),
                appointmentTime,
                reasonArea.getText(),
                status
        );

        try {
            Appointment savedAppointment = appointmentService.createAppointment(request);

            showInfo(
                    "Success",
                    "Appointment created successfully for "
                            + savedAppointment.getPatientName()
                            + " with "
                            + savedAppointment.getDoctorName()
            );

            goBackToAppointmentList(event);

        } catch (Exception e) {
            showError("Appointment Error", e.getMessage());
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        goBackToAppointmentList(event);
    }

    private void loadPatients() {
        for (Patient patient : patientService.getAllPatients()) {
            patientComboBox.getItems().add(
                    new ComboBoxItem(
                            patient.getId(),
                            patient.getFirstName() + " " + patient.getLastName()
                    )
            );
        }
    }

    private void loadDoctors() {
        for (Doctor doctor : doctorService.getAllDoctors()) {
            doctorComboBox.getItems().add(
                    new ComboBoxItem(
                            doctor.getId(),
                            "Dr. " + doctor.getFirstName() + " " + doctor.getLastName()
                    )
            );
        }
    }

    private void loadTimes() {
        appointmentTimeComboBox.getItems().addAll(
                "08:00",
                "09:00",
                "10:00",
                "11:00",
                "12:00",
                "13:00",
                "14:00",
                "15:00",
                "16:00"
        );
    }

    private void loadStatuses() {
        statusComboBox.getItems().addAll(AppointmentStatus.values());
        statusComboBox.setValue(AppointmentStatus.SCHEDULED);
    }

    private void goBackToAppointmentList(ActionEvent event) {
        changeScene(
                event,
                "/com/hospitalms/fxml/appointment/appointment-list-view.fxml",
                "Hospital Management System - Appointments"
        );
    }
}