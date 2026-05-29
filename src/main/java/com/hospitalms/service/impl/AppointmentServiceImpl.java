package com.hospitalms.service.impl;

import com.hospitalms.dto.appointment.AppointmentCreateRequest;
import com.hospitalms.model.Appointment;
import com.hospitalms.repository.AppointmentRepository;
import com.hospitalms.service.AppointmentService;
import com.hospitalms.dto.appointment.AppointmentUpdateRequest;

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
    public Appointment updateAppointment(Long id, AppointmentUpdateRequest request) {
        Appointment existingAppointment = getAppointmentById(id);

        Appointment updatedAppointment = new Appointment(
                existingAppointment.getId(),
                request.getPatientId(),
                request.getDoctorId(),
                existingAppointment.getPatientName(),
                existingAppointment.getDoctorName(),
                request.getAppointmentDate(),
                request.getAppointmentTime(),
                request.getReason(),
                request.getStatus()
        );

        return appointmentRepository.update(updatedAppointment);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + id));
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