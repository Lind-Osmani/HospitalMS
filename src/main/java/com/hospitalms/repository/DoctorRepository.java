package com.hospitalms.repository;

import com.hospitalms.core.repository.CrudRepository;
import com.hospitalms.model.Doctor;

import java.util.List;

public interface DoctorRepository extends CrudRepository<Doctor, Long> {

    List<Doctor> searchByNameOrSpecialization(String keyword);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);
}