package com.hospitalms.service;

import com.hospitalms.dto.doctor.DoctorCreateRequest;
import com.hospitalms.dto.doctor.DoctorUpdateRequest;
import com.hospitalms.model.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor createDoctor(DoctorCreateRequest request);

    Doctor updateDoctor(Long id, DoctorUpdateRequest request);

    Doctor getDoctorById(Long id);

    List<Doctor> getAllDoctors();

    List<Doctor> searchDoctors(String keyword);

    void deleteDoctor(Long id);
}