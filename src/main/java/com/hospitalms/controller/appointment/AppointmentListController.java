package com.hospitalms.controller.appointment;

import com.hospitalms.config.AppFactory;
import com.hospitalms.config.AppointmentDetailsContext;
import com.hospitalms.config.AppointmentFormContext;
import com.hospitalms.config.MedicalRecordContext;
import com.hospitalms.core.controller.BaseController;
import com.hospitalms.core.navigation.PageTitles;
import com.hospitalms.core.navigation.ViewPaths;
import com.hospitalms.dto.appointment.AppointmentResponse;
import com.hospitalms.mapper.AppointmentMapper;
import com.hospitalms.model.Appointment;
import com.hospitalms.model.AppointmentStatus;
import com.hospitalms.service.AppointmentService;
import com.hospitalms.service.MedicalRecordService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AppointmentListController extends BaseController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<AppointmentResponse> appointmentTable;

    @FXML
    private TableColumn<AppointmentResponse, Long> idColumn;

    @FXML
    private TableColumn<AppointmentResponse, String> patientNameColumn;

    @FXML
    private TableColumn<AppointmentResponse, String> doctorNameColumn;

    @FXML
    private TableColumn<AppointmentResponse, String> dateColumn;

    @FXML
    private TableColumn<AppointmentResponse, String> timeColumn;

    @FXML
    private TableColumn<AppointmentResponse, String> statusColumn;

    private final AppointmentService appointmentService = AppFactory.getAppointmentService();
    private final MedicalRecordService medicalRecordService = AppFactory.getMedicalRecordService();
    private final AppointmentMapper appointmentMapper = new AppointmentMapper();

    @FXML
    private void initialize() {
        setupTableColumns();
        loadAppointments();
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();

        List<Appointment> appointments = appointmentService.searchAppointments(keyword);
        showAppointmentsInTable(appointments);
    }

    @FXML
    private void handleRefresh() {
        searchField.clear();
        loadAppointments();
    }

    @FXML
    private void handleAddAppointment(ActionEvent event) {
        AppointmentFormContext.clear();
        AppointmentDetailsContext.clear();
        MedicalRecordContext.clear();

        changeScene(
                event,
                ViewPaths.APPOINTMENT_FORM,
                PageTitles.ADD_APPOINTMENT
        );
    }

    @FXML
    private void handleViewDetails(ActionEvent event) {
        AppointmentResponse selectedAppointment = getSelectedAppointment();

        if (selectedAppointment == null) {
            showError("Details Error", "Please select an appointment to view details.");
            return;
        }

        AppointmentDetailsContext.setSelectedAppointmentId(selectedAppointment.getId());

        changeScene(
                event,
                ViewPaths.APPOINTMENT_DETAILS,
                PageTitles.APPOINTMENT_DETAILS
        );
    }

    @FXML
    private void handleAddMedicalRecord(ActionEvent event) {
        AppointmentResponse selectedAppointment = getSelectedAppointment();

        if (selectedAppointment == null) {
            showError("Medical Record Error", "Please select an appointment first.");
            return;
        }

        Appointment appointment = appointmentService.getAppointmentById(selectedAppointment.getId());

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            showError(
                    "Medical Record Error",
                    "Only completed appointments can have a medical record."
            );
            return;
        }

        if (medicalRecordService.hasMedicalRecord(appointment.getId())) {
            showError(
                    "Medical Record Error",
                    "This appointment already has a medical record."
            );
            return;
        }

        MedicalRecordContext.setSelectedAppointmentId(appointment.getId());

        changeScene(
                event,
                ViewPaths.MEDICAL_RECORD_FORM,
                PageTitles.ADD_MEDICAL_RECORD
        );
    }

    @FXML
    private void handleViewMedicalRecord(ActionEvent event) {
        AppointmentResponse selectedAppointment = getSelectedAppointment();

        if (selectedAppointment == null) {
            showError("Medical Record Error", "Please select an appointment first.");
            return;
        }

        if (!medicalRecordService.hasMedicalRecord(selectedAppointment.getId())) {
            showError(
                    "Medical Record Error",
                    "This appointment does not have a medical record yet."
            );
            return;
        }

        MedicalRecordContext.setSelectedAppointmentId(selectedAppointment.getId());

        changeScene(
                event,
                ViewPaths.MEDICAL_RECORD_DETAILS,
                PageTitles.MEDICAL_RECORD_DETAILS
        );
    }

    @FXML
    private void handleEditAppointment(ActionEvent event) {
        AppointmentResponse selectedAppointment = getSelectedAppointment();

        if (selectedAppointment == null) {
            showError("Edit Error", "Please select an appointment to edit.");
            return;
        }

        AppointmentFormContext.setEditingAppointmentId(selectedAppointment.getId());

        changeScene(
                event,
                ViewPaths.APPOINTMENT_FORM,
                PageTitles.EDIT_APPOINTMENT
        );
    }

    @FXML
    private void handleDeleteAppointment() {
        AppointmentResponse selectedAppointment = getSelectedAppointment();

        if (selectedAppointment == null) {
            showError("Delete Error", "Please select an appointment to delete.");
            return;
        }

        try {
            appointmentService.deleteAppointment(selectedAppointment.getId());

            showInfo("Success", "Appointment deleted successfully.");

            loadAppointments();

        } catch (Exception e) {
            showError("Delete Error", e.getMessage());
        }
    }

    @FXML
    private void handleBackToDashboard(ActionEvent event) {
        AppointmentFormContext.clear();
        AppointmentDetailsContext.clear();
        MedicalRecordContext.clear();

        changeScene(
                event,
                ViewPaths.DASHBOARD,
                PageTitles.DASHBOARD
        );
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        doctorNameColumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        showAppointmentsInTable(appointments);
    }

    private void showAppointmentsInTable(List<Appointment> appointments) {
        List<AppointmentResponse> appointmentResponses =
                appointmentMapper.toResponseList(appointments);

        ObservableList<AppointmentResponse> observableAppointments =
                FXCollections.observableArrayList(appointmentResponses);

        appointmentTable.setItems(observableAppointments);
    }

    private AppointmentResponse getSelectedAppointment() {
        return appointmentTable.getSelectionModel().getSelectedItem();
    }
}