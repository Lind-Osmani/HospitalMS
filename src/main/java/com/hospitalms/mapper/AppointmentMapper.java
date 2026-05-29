package com.hospitalms.mapper;

import com.hospitalms.core.mapper.BaseMapper;
import com.hospitalms.dto.appointment.AppointmentResponse;
import com.hospitalms.model.Appointment;

import java.util.List;

public class AppointmentMapper implements BaseMapper<Appointment, AppointmentResponse> {

    @Override
    public AppointmentResponse toResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPatientName(),
                appointment.getDoctorName(),
                appointment.getAppointmentDate().toString(),
                appointment.getAppointmentTime().toString(),
                appointment.getStatus().name()
        );
    }

    @Override
    public List<AppointmentResponse> toResponseList(List<Appointment> appointments) {
        return appointments.stream()
                .map(this::toResponse)
                .toList();
    }
}