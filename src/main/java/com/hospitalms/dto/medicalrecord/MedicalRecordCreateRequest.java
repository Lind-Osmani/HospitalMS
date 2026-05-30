package com.hospitalms.dto.medicalrecord;

public class MedicalRecordCreateRequest {

    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private String diagnosis;
    private String treatment;
    private String prescription;
    private String notes;

    public MedicalRecordCreateRequest() {
    }

    public MedicalRecordCreateRequest(
            Long appointmentId,
            Long patientId,
            Long doctorId,
            String diagnosis,
            String treatment,
            String prescription,
            String notes
    ) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.prescription = prescription;
        this.notes = notes;
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
}