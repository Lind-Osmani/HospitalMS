package com.hospitalms.controller.appointment;

import com.hospitalms.config.AppFactory;
import com.hospitalms.core.controller.BaseController;
import com.hospitalms.dto.appointment.AppointmentResponse;
import com.hospitalms.mapper.AppointmentMapper;
import com.hospitalms.model.Appointment;
import com.hospitalms.service.AppointmentService;
import com.hospitalms.config.AppointmentFormContext;
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
    private TableColumn<AppointmentResponse, String> reasonColumn;

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

        changeScene(
                event,
                "/com/hospitalms/fxml/appointment/appointment-form-view.fxml",
                "Hospital Management System - Add Appointment"
        );
    }

    @FXML
    private void handleEditAppointment(ActionEvent event) {
        AppointmentResponse selectedAppointment = getSelectedTableItem(
                appointmentTable,
                "Edit Error",
                "Please select an appointment to edit."
        );

        if (selectedAppointment == null) {
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
        AppointmentResponse selectedAppointment = getSelectedTableItem(
                appointmentTable,
                "Delete Error",
                "Please select an appointment to delete."
        );

        if (selectedAppointment == null) {
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
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
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