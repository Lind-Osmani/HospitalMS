package com.hospitalms.service;

import com.hospitalms.core.service.BaseService;
import com.hospitalms.dto.patient.PatientCreateRequest;
import com.hospitalms.model.Patient;

public interface PatientService extends BaseService<PatientCreateRequest, Long> {

    Patient createPatient(PatientCreateRequest request);
}