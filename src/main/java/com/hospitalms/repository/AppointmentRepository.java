package com.hospitalms.repository;

import com.hospitalms.core.repository.CrudRepository;
import com.hospitalms.model.Appointment;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    List<Appointment> search(String keyword);
}