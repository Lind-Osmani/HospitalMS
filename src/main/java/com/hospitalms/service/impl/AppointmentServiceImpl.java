package com.hospitalms.service.impl;

import com.hospitalms.dto.appointment.AppointmentCreateRequest;
import com.hospitalms.model.Appointment;
import com.hospitalms.repository.AppointmentRepository;
import com.hospitalms.service.AppointmentService;

import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment createAppointment(AppointmentCreateRequest request) {
        Appointment appointment = new Appointment(
                null,
                request.getPatientId(),
                request.getDoctorId(),
                null,
                null,
                request.getAppointmentDate(),
                request.getAppointmentTime(),
                request.getReason(),
                request.getStatus()
        );

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> searchAppointments(String keyword) {
        return appointmentRepository.search(keyword);
    }
}