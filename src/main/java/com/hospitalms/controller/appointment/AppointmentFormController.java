package com.hospitalms.controller.appointment;

import com.hospitalms.config.AppFactory;
import com.hospitalms.config.AppointmentFormContext;
import com.hospitalms.core.controller.BaseController;
import com.hospitalms.core.ui.ComboBoxItem;
import com.hospitalms.dto.appointment.AppointmentCreateRequest;
import com.hospitalms.dto.appointment.AppointmentUpdateRequest;
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

        if (AppointmentFormContext.isEditMode()) {
            loadAppointmentForEditing();
        }
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

        if (AppointmentFormContext.isEditMode()) {
            updateAppointment(event, patientId, doctorId, appointmentTime, status);
        } else {
            createAppointment(event, patientId, doctorId, appointmentTime, status);
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        AppointmentFormContext.clear();
        goBackToAppointmentList(event);
    }

    private void createAppointment(
            ActionEvent event,
            Long patientId,
            Long doctorId,
            LocalTime appointmentTime,
            AppointmentStatus status
    ) {
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

    private void updateAppointment(
            ActionEvent event,
            Long patientId,
            Long doctorId,
            LocalTime appointmentTime,
            AppointmentStatus status
    ) {
        Long appointmentId = AppointmentFormContext.getEditingAppointmentId();

        AppointmentUpdateRequest request = new AppointmentUpdateRequest(
                patientId,
                doctorId,
                appointmentDatePicker.getValue(),
                appointmentTime,
                reasonArea.getText(),
                status
        );

        try {
            Appointment updatedAppointment = appointmentService.updateAppointment(appointmentId, request);

            showInfo(
                    "Success",
                    "Appointment updated successfully for "
                            + updatedAppointment.getPatientName()
                            + " with "
                            + updatedAppointment.getDoctorName()
            );

            AppointmentFormContext.clear();
            goBackToAppointmentList(event);

        } catch (Exception e) {
            showError("Appointment Error", e.getMessage());
        }
    }

    private void loadAppointmentForEditing() {
        Long appointmentId = AppointmentFormContext.getEditingAppointmentId();

        Appointment appointment = appointmentService.getAppointmentById(appointmentId);

        selectComboBoxItemById(patientComboBox, appointment.getPatientId());
        selectComboBoxItemById(doctorComboBox, appointment.getDoctorId());

        appointmentDatePicker.setValue(appointment.getAppointmentDate());
        appointmentTimeComboBox.setValue(appointment.getAppointmentTime().toString());
        statusComboBox.setValue(appointment.getStatus());
        reasonArea.setText(appointment.getReason());
    }

    private void selectComboBoxItemById(ComboBox<ComboBoxItem> comboBox, Long id) {
        for (ComboBoxItem item : comboBox.getItems()) {
            if (item.getId().equals(id)) {
                comboBox.setValue(item);
                return;
            }
        }
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