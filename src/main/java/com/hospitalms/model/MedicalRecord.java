package com.hospitalms.model;

import java.time.LocalDateTime;

public class MedicalRecord {

    private Long id;
    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private String patientName;
    private String doctorName;
    private String diagnosis;
    private String treatment;
    private String prescription;
    private String notes;
    private LocalDateTime createdAt;

    public MedicalRecord() {
    }

    public MedicalRecord(
            Long id,
            Long appointmentId,
            Long patientId,
            Long doctorId,
            String patientName,
            String doctorName,
            String diagnosis,
            String treatment,
            String prescription,
            String notes,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.prescription = prescription;
        this.notes = notes;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public String getPrescription() {
        return prescription;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}