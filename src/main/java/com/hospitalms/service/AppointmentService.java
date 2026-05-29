package com.hospitalms.service;

import com.hospitalms.dto.appointment.AppointmentCreateRequest;
import com.hospitalms.model.Appointment;

import java.util.List;

public interface AppointmentService {

    Appointment createAppointment(AppointmentCreateRequest request);

    List<Appointment> getAllAppointments();

    List<Appointment> searchAppointments(String keyword);
}