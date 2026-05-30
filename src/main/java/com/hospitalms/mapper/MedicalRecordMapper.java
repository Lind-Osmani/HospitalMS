package com.hospitalms.mapper;

import com.hospitalms.core.mapper.BaseMapper;
import com.hospitalms.dto.medicalrecord.MedicalRecordResponse;
import com.hospitalms.model.MedicalRecord;

import java.util.List;

public class MedicalRecordMapper implements BaseMapper<MedicalRecord, MedicalRecordResponse> {

    @Override
    public MedicalRecordResponse toResponse(MedicalRecord medicalRecord) {
        return new MedicalRecordResponse(
                medicalRecord.getId(),
                medicalRecord.getPatientName(),
                medicalRecord.getDoctorName(),
                medicalRecord.getDiagnosis(),
                medicalRecord.getTreatment(),
                medicalRecord.getPrescription(),
                medicalRecord.getNotes(),
                medicalRecord.getCreatedAt() != null
                        ? medicalRecord.getCreatedAt().toString()
                        : ""
        );
    }

    @Override
    public List<MedicalRecordResponse> toResponseList(List<MedicalRecord> medicalRecords) {
        return medicalRecords.stream()
                .map(this::toResponse)
                .toList();
    }
}