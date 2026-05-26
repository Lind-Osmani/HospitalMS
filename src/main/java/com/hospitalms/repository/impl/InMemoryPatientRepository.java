package com.hospitalms.repository.impl;

import com.hospitalms.model.Patient;
import com.hospitalms.repository.PatientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryPatientRepository implements PatientRepository {

    private final List<Patient> patients = new ArrayList<>();
    private long nextId = 1;

    @Override
    public Patient save(Patient patient) {
        Patient patientWithId = new Patient(
                nextId++,
                patient.getFirstName(),
                patient.getLastName(),
                patient.getGender(),
                patient.getDateOfBirth(),
                patient.getPhone(),
                patient.getEmail(),
                patient.getBloodGroup(),
                patient.getAddress()
        );

        patients.add(patientWithId);
        return patientWithId;
    }

    @Override
    public Patient update(Patient patient) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getId().equals(patient.getId())) {
                patients.set(i, patient);
                return patient;
            }
        }

        throw new IllegalArgumentException("Patient not found with ID: " + patient.getId());
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return patients.stream()
                .filter(patient -> patient.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Patient> findAll() {
        return new ArrayList<>(patients);
    }

    @Override
    public boolean deleteById(Long id) {
        return patients.removeIf(patient -> patient.getId().equals(id));
    }

    @Override
    public List<Patient> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }

        String lowerKeyword = keyword.toLowerCase();

        return patients.stream()
                .filter(patient ->
                        patient.getFirstName().toLowerCase().contains(lowerKeyword)
                                || patient.getLastName().toLowerCase().contains(lowerKeyword)
                )
                .toList();
    }

    @Override
    public boolean existsByPhone(String phone) {
        return patients.stream()
                .anyMatch(patient -> patient.getPhone().equals(phone));
    }

    @Override
    public boolean existsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        return patients.stream()
                .anyMatch(patient -> email.equals(patient.getEmail()));
    }

    @Override
    public boolean existsByPhoneAndIdNot(String phone, Long id) {
        return patients.stream()
                .anyMatch(patient ->
                        patient.getPhone().equals(phone)
                                && !patient.getId().equals(id)
                );
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, Long id) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        return patients.stream()
                .anyMatch(patient ->
                        email.equals(patient.getEmail())
                                && !patient.getId().equals(id)
                );
    }
}