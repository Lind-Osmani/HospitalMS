package com.hospitalms.service;

import com.hospitalms.dto.medicalrecord.MedicalRecordCreateRequest;
import com.hospitalms.model.MedicalRecord;

public interface MedicalRecordService {

    MedicalRecord createMedicalRecord(MedicalRecordCreateRequest request);

    MedicalRecord getMedicalRecordByAppointmentId(Long appointmentId);

    boolean hasMedicalRecord(Long appointmentId);
}