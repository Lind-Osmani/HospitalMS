package com.hospitalms.controller.appointment;

import com.hospitalms.config.AppFactory;
import com.hospitalms.config.AppointmentDetailsContext;
import com.hospitalms.core.controller.BaseController;
import com.hospitalms.dto.appointment.AppointmentResponse;
import com.hospitalms.mapper.AppointmentMapper;
import com.hospitalms.model.Appointment;
import com.hospitalms.service.AppointmentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.hospitalms.config.AppointmentFormContext;

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

        changeScene(
                event,
                "/com/hospitalms/fxml/appointment/appointment-form-view.fxml",
                "Hospital Management System - Add Appointment"
        );
    }

    @FXML
    private void handleViewDetails(ActionEvent event) {
        AppointmentResponse selectedAppointment =
                appointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            showError("Details Error", "Please select an appointment to view details.");
            return;
        }

        AppointmentDetailsContext.setSelectedAppointmentId(selectedAppointment.getId());

        changeScene(
                event,
                "/com/hospitalms/fxml/appointment/appointment-details-view.fxml",
                "Hospital Management System - Appointment Details"
        );
    }

    @FXML
    private void handleEditAppointment(ActionEvent event) {
        AppointmentResponse selectedAppointment =
                appointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            showError("Edit Error", "Please select an appointment to edit.");
            return;
        }

        AppointmentFormContext.setEditingAppointmentId(selectedAppointment.getId());

        changeScene(
                event,
                "/com/hospitalms/fxml/appointment/appointment-form-view.fxml",
                "Hospital Management System - Edit Appointment"
        );
    }

    @FXML
    private void handleDeleteAppointment() {
        AppointmentResponse selectedAppointment =
                appointmentTable.getSelectionModel().getSelectedItem();

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
        AppointmentDetailsContext.clear();

        changeScene(
                event,
                "/com/hospitalms/fxml/dashboard/dashboard-view.fxml",
                "Hospital Management System - Dashboard"
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
}