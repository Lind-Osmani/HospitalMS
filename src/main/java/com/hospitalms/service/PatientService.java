package com.hospitalms.service;

import com.hospitalms.dto.patient.PatientCreateRequest;
import com.hospitalms.model.Patient;

import java.util.List;

public interface PatientService {

    Patient createPatient(PatientCreateRequest request);

    List<Patient> getAllPatients();

    List<Patient> searchPatients(String keyword);

    void deletePatient(Long id);
}