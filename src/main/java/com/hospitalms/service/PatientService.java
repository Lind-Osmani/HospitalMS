package com.hospitalms.service;

import com.hospitalms.dto.patient.PatientCreateRequest;
import com.hospitalms.dto.patient.PatientUpdateRequest;
import com.hospitalms.model.Patient;

import java.util.List;

public interface PatientService {

    Patient createPatient(PatientCreateRequest request);

    Patient updatePatient(Long id, PatientUpdateRequest request);

    Patient getPatientById(Long id);

    List<Patient> getAllPatients();

    List<Patient> searchPatients(String keyword);

    void deletePatient(Long id);
}