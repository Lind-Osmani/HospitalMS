package com.hospitalms.controller.dashboard;

import com.hospitalms.core.controller.BaseController;
import com.hospitalms.core.navigation.PageTitles;
import com.hospitalms.core.navigation.ViewPaths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class DashboardController extends BaseController {

    @FXML
    private void handlePatients(ActionEvent event) {
        changeScene(
                event,
                ViewPaths.PATIENT_LIST,
                PageTitles.PATIENTS
        );
    }

    @FXML
    private void handleDoctors(ActionEvent event) {
        changeScene(
                event,
                ViewPaths.DOCTOR_LIST,
                PageTitles.DOCTORS
        );
    }

    @FXML
    private void handleAppointments(ActionEvent event) {
        changeScene(
                event,
                ViewPaths.APPOINTMENT_LIST,
                PageTitles.APPOINTMENTS
        );
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        changeScene(
                event,
                ViewPaths.LOGIN,
                PageTitles.LOGIN
        );
    }
}