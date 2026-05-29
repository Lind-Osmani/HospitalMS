package com.hospitalms.service;

import com.hospitalms.dto.appointment.AppointmentCreateRequest;
import com.hospitalms.dto.appointment.AppointmentUpdateRequest;
import com.hospitalms.model.Appointment;

import java.util.List;

public interface AppointmentService {

    Appointment createAppointment(AppointmentCreateRequest request);

    Appointment updateAppointment(Long id, AppointmentUpdateRequest request);

    Appointment getAppointmentById(Long id);

    List<Appointment> getAllAppointments();

    List<Appointment> searchAppointments(String keyword);
}