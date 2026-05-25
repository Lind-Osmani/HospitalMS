package com.hospitalms.service.impl;

import com.hospitalms.dto.patient.PatientCreateRequest;
import com.hospitalms.dto.patient.PatientUpdateRequest;
import com.hospitalms.model.Patient;
import com.hospitalms.repository.PatientRepository;
import com.hospitalms.service.PatientService;

import java.util.List;

public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient createPatient(PatientCreateRequest request) {
        Patient patient = new Patient(
                null,
                request.getFirstName(),
                request.getLastName(),
                request.getGender(),
                request.getDateOfBirth(),
                request.getPhone(),
                request.getEmail(),
                request.getBloodGroup(),
                request.getAddress()
        );

        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long id, PatientUpdateRequest request) {
        Patient existingPatient = getPatientById(id);

        Patient updatedPatient = new Patient(
                existingPatient.getId(),
                request.getFirstName(),
                request.getLastName(),
                request.getGender(),
                request.getDateOfBirth(),
                request.getPhone(),
                request.getEmail(),
                request.getBloodGroup(),
                request.getAddress()
        );

        return patientRepository.update(updatedPatient);
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + id));
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public List<Patient> searchPatients(String keyword) {
        return patientRepository.searchByName(keyword);
    }

    @Override
    public void deletePatient(Long id) {
        boolean deleted = patientRepository.deleteById(id);

        if (!deleted) {
            throw new IllegalArgumentException("Patient not found with ID: " + id);
        }
    }
}