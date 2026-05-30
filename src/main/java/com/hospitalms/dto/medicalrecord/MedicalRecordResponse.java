package com.hospitalms.dto.medicalrecord;

public class MedicalRecordResponse {

    private Long id;
    private String patientName;
    private String doctorName;
    private String diagnosis;
    private String treatment;
    private String prescription;
    private String notes;
    private String createdAt;

    public MedicalRecordResponse() {
    }

    public MedicalRecordResponse(
            Long id,
            String patientName,
            String doctorName,
            String diagnosis,
            String treatment,
            String prescription,
            String notes,
            String createdAt
    ) {
        this.id = id;
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

    public String getCreatedAt() {
        return createdAt;
    }
}