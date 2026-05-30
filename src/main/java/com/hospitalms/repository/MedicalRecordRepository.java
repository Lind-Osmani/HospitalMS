package com.hospitalms.repository;

import com.hospitalms.core.repository.CrudRepository;
import com.hospitalms.model.MedicalRecord;

import java.util.Optional;

public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Long> {

    Optional<MedicalRecord> findByAppointmentId(Long appointmentId);

    boolean existsByAppointmentId(Long appointmentId);
}