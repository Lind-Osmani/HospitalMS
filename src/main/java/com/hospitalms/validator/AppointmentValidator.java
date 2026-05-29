package com.hospitalms.validator;

import com.hospitalms.model.AppointmentStatus;
import com.hospitalms.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentValidator {

    public String validate(
            Long patientId,
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            String reason,
            AppointmentStatus status
    ) {
        if (patientId == null) {
            return "Patient is required.";
        }

        if (doctorId == null) {
            return "Doctor is required.";
        }

        if (appointmentDate == null) {
            return "Appointment date is required.";
        }

        if (appointmentTime == null) {
            return "Appointment time is required.";
        }

        if (appointmentDate.isBefore(LocalDate.now())) {
            return "Appointment date cannot be in the past.";
        }

        if (ValidationUtil.isBlank(reason)) {
            return "Appointment reason is required.";
        }

        if (status == null) {
            return "Appointment status is required.";
        }

        return null;
    }
}