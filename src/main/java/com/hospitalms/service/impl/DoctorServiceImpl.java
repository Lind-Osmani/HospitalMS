package com.hospitalms.service.impl;


import com.hospitalms.dto.doctor.DoctorCreateRequest;
import com.hospitalms.exception.ValidationException;
import com.hospitalms.model.Doctor;
import com.hospitalms.repository.DoctorRepository;
import com.hospitalms.service.DoctorService;

import java.util.List;

public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor createDoctor(DoctorCreateRequest request) {
        validateUniqueDoctor(request);

        Doctor doctor = new Doctor(
                null,
                request.getFirstName(),
                request.getLastName(),
                request.getSpecialization(),
                request.getDepartment(),
                request.getPhone(),
                request.getEmail(),
                request.getAddress()
        );

        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    @Override
    public List<Doctor> searchDoctors(String keyword) {
        return doctorRepository.searchByNameOrSpecialization(keyword);
    }

    private void validateUniqueDoctor(DoctorCreateRequest request) {
        if (doctorRepository.existsByPhone(request.getPhone())) {
            throw new ValidationException("A doctor with this phone number already exists.");
        }

        if (doctorRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("A doctor with this email already exists.");
        }
    }
}






