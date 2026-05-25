package com.hospitalms.mapper;

import com.hospitalms.core.mapper.BaseMapper;
import com.hospitalms.dto.patient.PatientResponse;
import com.hospitalms.model.Patient;

import java.util.List;

public class PatientMapper implements BaseMapper<Patient, PatientResponse> {

    @Override
    public PatientResponse toResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhone(),
                patient.getEmail()
        );
    }

    @Override
    public List<PatientResponse> toResponseList(List<Patient> patients) {
        return patients.stream()
                .map(this::toResponse)
                .toList();
    }
}