package com.hospitalms.service;

import com.hospitalms.dto.doctor.DoctorCreateRequest;
import com.hospitalms.model.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor createDoctor(DoctorCreateRequest request);

    List<Doctor> getAllDoctors();

    List<Doctor> searchDoctors(String keyword);

}