package com.hospitalms.controller.medicalrecord;

import com.hospitalms.config.AppFactory;
import com.hospitalms.config.MedicalRecordContext;
import com.hospitalms.core.controller.BaseController;
import com.hospitalms.core.navigation.PageTitles;
import com.hospitalms.core.navigation.ViewPaths;
import com.hospitalms.model.MedicalRecord;
import com.hospitalms.service.MedicalRecordService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MedicalRecordDetailsController extends BaseController {

    @FXML
    private Label patientNameLabel;

    @FXML
    private Label doctorNameLabel;

    @FXML
    private Label createdAtLabel;

    @FXML
    private Label diagnosisLabel;

    @FXML
    private Label treatmentLabel;

    @FXML
    private Label prescriptionLabel;

    @FXML
    private Label notesLabel;

    private final MedicalRecordService medicalRecordService =
            AppFactory.getMedicalRecordService();

    @FXML
    private void initialize() {
        if (!MedicalRecordContext.hasSelectedAppointment()) {
            showError("Medical Record Error", "No appointment was selected.");
            return;
        }

        loadMedicalRecordDetails();
    }

    @FXML
    private void handleBack(ActionEvent event) {
        MedicalRecordContext.clear();

        changeScene(
                event,
                ViewPaths.APPOINTMENT_LIST,
                PageTitles.APPOINTMENTS
        );
    }

    private void loadMedicalRecordDetails() {
        Long appointmentId = MedicalRecordContext.getSelectedAppointmentId();

        MedicalRecord medicalRecord =
                medicalRecordService.getMedicalRecordByAppointmentId(appointmentId);

        patientNameLabel.setText(medicalRecord.getPatientName());
        doctorNameLabel.setText(medicalRecord.getDoctorName());
        createdAtLabel.setText(
                medicalRecord.getCreatedAt() != null
                        ? medicalRecord.getCreatedAt().toString()
                        : ""
        );
        diagnosisLabel.setText(medicalRecord.getDiagnosis());
        treatmentLabel.setText(medicalRecord.getTreatment());
        prescriptionLabel.setText(medicalRecord.getPrescription());
        notesLabel.setText(medicalRecord.getNotes());
    }
}