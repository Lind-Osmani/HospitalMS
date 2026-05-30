package com.hospitalms.controller.medicalrecord;

import com.hospitalms.config.AppFactory;
import com.hospitalms.config.MedicalRecordContext;
import com.hospitalms.core.controller.BaseController;
import com.hospitalms.core.navigation.PageTitles;
import com.hospitalms.core.navigation.ViewPaths;
import com.hospitalms.dto.medicalrecord.MedicalRecordCreateRequest;
import com.hospitalms.model.Appointment;
import com.hospitalms.model.MedicalRecord;
import com.hospitalms.service.AppointmentService;
import com.hospitalms.service.MedicalRecordService;
import com.hospitalms.validator.MedicalRecordValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class MedicalRecordFormController extends BaseController {

    @FXML
    private Label patientNameLabel;

    @FXML
    private Label doctorNameLabel;

    @FXML
    private TextArea diagnosisArea;

    @FXML
    private TextArea treatmentArea;

    @FXML
    private TextArea prescriptionArea;

    @FXML
    private TextArea notesArea;

    private final AppointmentService appointmentService = AppFactory.getAppointmentService();
    private final MedicalRecordService medicalRecordService = AppFactory.getMedicalRecordService();
    private final MedicalRecordValidator medicalRecordValidator = new MedicalRecordValidator();

    private Appointment selectedAppointment;

    @FXML
    private void initialize() {
        if (!MedicalRecordContext.hasSelectedAppointment()) {
            showError("Medical Record Error", "No appointment was selected.");
            return;
        }

        loadSelectedAppointment();
    }

    @FXML
    private void handleSaveMedicalRecord(ActionEvent event) {
        String validationError = medicalRecordValidator.validate(
                diagnosisArea.getText(),
                treatmentArea.getText(),
                prescriptionArea.getText(),
                notesArea.getText()
        );

        if (validationError != null) {
            showError("Validation Error", validationError);
            return;
        }

        MedicalRecordCreateRequest request = new MedicalRecordCreateRequest(
                selectedAppointment.getId(),
                selectedAppointment.getPatientId(),
                selectedAppointment.getDoctorId(),
                diagnosisArea.getText(),
                treatmentArea.getText(),
                prescriptionArea.getText(),
                notesArea.getText()
        );

        try {
            MedicalRecord savedRecord = medicalRecordService.createMedicalRecord(request);

            showInfo(
                    "Success",
                    "Medical record created successfully for "
                            + savedRecord.getPatientName()
            );

            MedicalRecordContext.clear();
            goBackToAppointments(event);

        } catch (Exception e) {
            showError("Medical Record Error", e.getMessage());
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        MedicalRecordContext.clear();
        goBackToAppointments(event);
    }

    private void loadSelectedAppointment() {
        Long appointmentId = MedicalRecordContext.getSelectedAppointmentId();

        selectedAppointment = appointmentService.getAppointmentById(appointmentId);

        patientNameLabel.setText(selectedAppointment.getPatientName());
        doctorNameLabel.setText(selectedAppointment.getDoctorName());
    }

    private void goBackToAppointments(ActionEvent event) {
        changeScene(
                event,
                ViewPaths.APPOINTMENT_LIST,
                PageTitles.APPOINTMENTS
        );
    }
}
