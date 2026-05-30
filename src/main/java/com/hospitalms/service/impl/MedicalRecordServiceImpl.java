package com.hospitalms.service.impl;

import com.hospitalms.dto.medicalrecord.MedicalRecordCreateRequest;
import com.hospitalms.exception.ValidationException;
import com.hospitalms.model.Appointment;
import com.hospitalms.model.AppointmentStatus;
import com.hospitalms.model.MedicalRecord;
import com.hospitalms.repository.MedicalRecordRepository;
import com.hospitalms.service.AppointmentService;
import com.hospitalms.service.MedicalRecordService;

import java.time.LocalDateTime;

public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentService appointmentService;

    public MedicalRecordServiceImpl(
            MedicalRecordRepository medicalRecordRepository,
            AppointmentService appointmentService
    ) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.appointmentService = appointmentService;
    }

    @Override
    public MedicalRecord createMedicalRecord(MedicalRecordCreateRequest request) {
        validateAppointmentCanHaveMedicalRecord(request.getAppointmentId());

        MedicalRecord medicalRecord = new MedicalRecord(
                null,
                request.getAppointmentId(),
                request.getPatientId(),
                request.getDoctorId(),
                null,
                null,
                request.getDiagnosis(),
                request.getTreatment(),
                request.getPrescription(),
                request.getNotes(),
                LocalDateTime.now()
        );

        return medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public MedicalRecord getMedicalRecordByAppointmentId(Long appointmentId) {
        return medicalRecordRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Medical record not found for appointment ID: " + appointmentId
                ));
    }

    @Override
    public boolean hasMedicalRecord(Long appointmentId) {
        return medicalRecordRepository.existsByAppointmentId(appointmentId);
    }

    private void validateAppointmentCanHaveMedicalRecord(Long appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new ValidationException("Only completed appointments can have a medical record.");
        }

        if (medicalRecordRepository.existsByAppointmentId(appointmentId)) {
            throw new ValidationException("This appointment already has a medical record.");
        }
    }
}