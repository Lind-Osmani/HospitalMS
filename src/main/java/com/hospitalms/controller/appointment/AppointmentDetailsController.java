package com.hospitalms.controller.appointment;

import com.hospitalms.config.AppFactory;
import com.hospitalms.config.AppointmentDetailsContext;
import com.hospitalms.core.controller.BaseController;
import com.hospitalms.model.Appointment;
import com.hospitalms.service.AppointmentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AppointmentDetailsController extends BaseController {

    @FXML
    private Label idLabel;

    @FXML
    private Label patientNameLabel;

    @FXML
    private Label doctorNameLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label reasonLabel;

    private final AppointmentService appointmentService = AppFactory.getAppointmentService();

    @FXML
    private void initialize() {
        if (!AppointmentDetailsContext.hasSelectedAppointment()) {
            showError("Details Error", "No appointment was selected.");
            return;
        }

        loadAppointmentDetails();
    }

    @FXML
    private void handleBack(ActionEvent event) {
        AppointmentDetailsContext.clear();

        changeScene(
                event,
                "/com/hospitalms/fxml/appointment/appointment-list-view.fxml",
                "Hospital Management System - Appointments"
        );
    }

    private void loadAppointmentDetails() {
        Long appointmentId = AppointmentDetailsContext.getSelectedAppointmentId();

        Appointment appointment = appointmentService.getAppointmentById(appointmentId);

        idLabel.setText(String.valueOf(appointment.getId()));
        patientNameLabel.setText(appointment.getPatientName());
        doctorNameLabel.setText(appointment.getDoctorName());
        dateLabel.setText(appointment.getAppointmentDate().toString());
        timeLabel.setText(appointment.getAppointmentTime().toString());
        statusLabel.setText(appointment.getStatus().getDisplayName());
        reasonLabel.setText(appointment.getReason());
    }
}