package com.hospitalms.repository;

import com.hospitalms.core.repository.CrudRepository;
import com.hospitalms.model.Patient;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, Long> {

    List<Patient> searchByName(String keyword);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByPhoneAndIdNot(String phone, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);
}