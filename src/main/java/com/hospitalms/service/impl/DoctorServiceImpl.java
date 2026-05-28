package com.hospitalms.service.impl;

import com.hospitalms.dto.doctor.DoctorCreateRequest;
import com.hospitalms.dto.doctor.DoctorUpdateRequest;
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
        validateUniqueDoctorForCreate(request);

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
    public Doctor updateDoctor(Long id, DoctorUpdateRequest request) {
        Doctor existingDoctor = getDoctorById(id);

        validateUniqueDoctorForUpdate(id, request);

        Doctor updatedDoctor = new Doctor(
                existingDoctor.getId(),
                request.getFirstName(),
                request.getLastName(),
                request.getSpecialization(),
                request.getDepartment(),
                request.getPhone(),
                request.getEmail(),
                request.getAddress()
        );

        return doctorRepository.update(updatedDoctor);
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + id));
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public List<Doctor> searchDoctors(String keyword) {
        return doctorRepository.searchByNameOrSpecialization(keyword);
    }

    @Override
    public void deleteDoctor(Long id) {
        boolean deleted = doctorRepository.deleteById(id);

        if (!deleted) {
            throw new IllegalArgumentException("Doctor not found with ID: " + id);
        }
    }

    private void validateUniqueDoctorForCreate(DoctorCreateRequest request) {
        if (doctorRepository.existsByPhone(request.getPhone())) {
            throw new ValidationException("A doctor with this phone number already exists.");
        }

        if (doctorRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("A doctor with this email already exists.");
        }
    }

    private void validateUniqueDoctorForUpdate(Long doctorId, DoctorUpdateRequest request) {
        if (doctorRepository.existsByPhoneAndIdNot(request.getPhone(), doctorId)) {
            throw new ValidationException("Another doctor with this phone number already exists.");
        }

        if (doctorRepository.existsByEmailAndIdNot(request.getEmail(), doctorId)) {
            throw new ValidationException("Another doctor with this email already exists.");
        }
    }
}