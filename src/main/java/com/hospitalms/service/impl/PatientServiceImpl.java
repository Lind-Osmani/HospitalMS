package com.hospitalms.service.impl;

import com.hospitalms.dto.patient.PatientCreateRequest;
import com.hospitalms.model.Patient;
import com.hospitalms.service.PatientService;

import java.util.ArrayList;
import java.util.List;

public class PatientServiceImpl implements PatientService {

    private final List<Patient> patients = new ArrayList<>();
    private long nextId = 1;

    @Override
    public Patient createPatient(PatientCreateRequest request) {
        Patient patient = new Patient(
                nextId++,
                request.getFirstName(),
                request.getLastName(),
                request.getGender(),
                request.getDateOfBirth(),
                request.getPhone(),
                request.getEmail(),
                request.getBloodGroup(),
                request.getAddress()
        );

        patients.add(patient);

        return patient;
    }

    @Override
    public PatientCreateRequest create(PatientCreateRequest request) {
        createPatient(request);
        return request;
    }

    @Override
    public PatientCreateRequest update(Long id, PatientCreateRequest request) {
        throw new UnsupportedOperationException("Update patient will be implemented later.");
    }

    @Override
    public PatientCreateRequest findById(Long id) {
        throw new UnsupportedOperationException("Find patient by ID will be implemented later.");
    }

    @Override
    public List<PatientCreateRequest> findAll() {
        throw new UnsupportedOperationException("Find all patients will be implemented later.");
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("Delete patient will be implemented later.");
    }
}