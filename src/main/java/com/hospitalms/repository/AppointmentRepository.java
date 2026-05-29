package com.hospitalms.repository;

import com.hospitalms.core.repository.CrudRepository;
import com.hospitalms.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    List<Appointment> search(String keyword);

    boolean existsByDoctorAndDateTime(Long doctorId, LocalDate date, LocalTime time);

    boolean existsByDoctorAndDateTimeAndIdNot(Long doctorId, LocalDate date, LocalTime time, Long appointmentId);
}