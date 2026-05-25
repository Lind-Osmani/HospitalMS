package com.hospitalms.repository;

import com.hospitalms.core.repository.CrudRepository;
import com.hospitalms.model.Patient;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, Long> {

    List<Patient> searchByName(String keyword);
}